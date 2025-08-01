import os

def gerar_estrutura_projeto(diretorio, arquivo_saida):
    with open(arquivo_saida, 'w', encoding='utf-8') as f:
        for raiz, diretorios, arquivos in os.walk(diretorio):
            nivel = raiz.replace(diretorio, '').count(os.sep)
            indentacao = '│   ' * nivel
            f.write(f"{indentacao}├── {os.path.basename(raiz)}\n")
            sub_indentacao = '│   ' * (nivel + 1)
            for arquivo in arquivos:
                if arquivo.endswith('.java'):
                    f.write(f"{sub_indentacao}└── {arquivo}\n")

diretorio_projeto = input("Digite o caminho do diretório do módulo: ").strip()
arquivo_saida = "estrutura_modulo.txt"
gerar_estrutura_projeto(diretorio_projeto, arquivo_saida)
print(f"Estrutura gerada em: {arquivo_saida}")