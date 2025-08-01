import os
import json
from bs4 import BeautifulSoup

# ====== CONFIGURAÇÕES ======
HTML_ITINERARIO = 'itinerario-onibus.html'
PASTA_JSON = 'json_pontos'
PASTA_SAIDA = 'json_atualizados'
# ============================


def carregar_itinerarios(rotas_filtradas):
    with open(HTML_ITINERARIO, 'r', encoding='utf-8') as file:
        soup = BeautifulSoup(file, 'html.parser')

    tabelas = soup.find_all('table')
    itinerarios = {'EMBARQUE': {}, 'DESEMBARQUE': {}}

    for i, tabela in enumerate(tabelas):
        linhas = tabela.find_all('tr')[2:]  # Ignorar cabeçalhos

        tipo = 'EMBARQUE' if i == 0 else 'DESEMBARQUE'

        for linha in linhas:
            colunas = linha.find_all('td')
            if len(colunas) < 3:
                continue

            horario = colunas[0].get_text(strip=True)
            rota = colunas[1].get_text(strip=True).upper()
            paradas = [li.get_text(strip=True) for li in colunas[2].find_all('li')]

            if rotas_filtradas and rota not in rotas_filtradas:
                continue  # pula se a rota não estiver no filtro

            for parada in paradas:
                if parada not in itinerarios[tipo]:
                    itinerarios[tipo][parada] = []

                if horario not in itinerarios[tipo][parada]:
                    itinerarios[tipo][parada].append(horario)

    return itinerarios


def atualizar_json(itinerarios, rotas_filtradas):
    if not os.path.exists(PASTA_SAIDA):
        os.makedirs(PASTA_SAIDA)

    arquivos = [f for f in os.listdir(PASTA_JSON) if f.endswith('.json')]

    for arquivo in arquivos:
        caminho = os.path.join(PASTA_JSON, arquivo)

        with open(caminho, 'r', encoding='utf-8') as f:
            dados = json.load(f)

        tipo_ponto = dados.get('tipoPonto', '').upper()  # EMBARQUE, DESEMBARQUE ou AMBOS
        tag_rota = dados.get('tagRota', '').upper()

        if tipo_ponto not in ['EMBARQUE', 'DESEMBARQUE', 'AMBOS']:
            print(f'⚠️ tipoPonto inválido no arquivo {arquivo}, pulando...')
            continue

        horarios_encontrados = []

        # Processar os dois sentidos se for 'AMBOS'
        sentidos = []
        if tipo_ponto in ['EMBARQUE', 'AMBOS']:
            sentidos.append('EMBARQUE')
        if tipo_ponto in ['DESEMBARQUE', 'AMBOS']:
            sentidos.append('DESEMBARQUE')

        for sentido in sentidos:
            for rota, horarios_por_parada in extrair_rotas_por_sentido(itinerarios[sentido], rotas_filtradas, tag_rota):
                for horario in horarios_por_parada:
                    horarios_encontrados.append({
                        'horario': formatar_horario(horario),
                        'tipoPonto': sentido
                    })

        # Remover duplicatas de horário
        horarios_unicos = {h['horario']: h for h in horarios_encontrados}
        dados['horarios'] = list(horarios_unicos.values())

        # Salvar arquivo atualizado
        caminho_saida = os.path.join(PASTA_SAIDA, arquivo)
        with open(caminho_saida, 'w', encoding='utf-8') as f:
            json.dump(dados, f, ensure_ascii=False, indent=4)

        print(f'✅ Arquivo atualizado: {arquivo}')


def extrair_rotas_por_sentido(itinerario_sentido, rotas_filtradas, tag_rota):
    resultado = []
    for parada, horarios in itinerario_sentido.items():
        for rota in rotas_filtradas:
            if tag_rota in rota:
                resultado.append((rota, horarios))
                break
    return resultado


def formatar_horario(horario):
    """Garante que o horário esteja no formato HH:MM:SS"""
    partes = horario.strip().split(':')
    if len(partes) == 2:
        return f'{partes[0]}:{partes[1]}:00'
    elif len(partes) == 3:
        return horario
    else:
        return f'{horario}:00'  # fallback


def menu_rotas_disponiveis():
    with open(HTML_ITINERARIO, 'r', encoding='utf-8') as file:
        soup = BeautifulSoup(file, 'html.parser')

    rotas = set()
    for tabela in soup.find_all('table'):
        for linha in tabela.find_all('tr')[2:]:
            colunas = linha.find_all('td')
            if len(colunas) >= 2:
                rota = colunas[1].get_text(strip=True).upper()
                rotas.add(rota)

    rotas_ordenadas = sorted(list(rotas))
    print("\n📜 Rotas disponíveis:")
    for idx, rota in enumerate(rotas_ordenadas, start=1):
        print(f"[{idx}] {rota}")

    escolhas = input("\nDigite os números das rotas que deseja considerar (separados por vírgula) ou pressione ENTER para todas: ")
    if not escolhas.strip():
        return rotas_ordenadas

    indices = [int(x.strip()) for x in escolhas.split(',') if x.strip().isdigit()]
    selecionadas = [rotas_ordenadas[i - 1] for i in indices if 0 < i <= len(rotas_ordenadas)]

    return selecionadas


def main():
    print('🔎 Lendo itinerário e listando rotas disponíveis...')
    rotas_filtradas = menu_rotas_disponiveis()

    print(f'\n🚌 Rotas selecionadas: {rotas_filtradas}')

    print('\n🔄 Carregando itinerário...')
    itinerarios = carregar_itinerarios(rotas_filtradas)

    print('📑 Atualizando arquivos JSON...')
    atualizar_json(itinerarios, rotas_filtradas)

    print('\n✅ Processo finalizado! Arquivos atualizados estão na pasta:', PASTA_SAIDA)


if __name__ == '__main__':
    main()
