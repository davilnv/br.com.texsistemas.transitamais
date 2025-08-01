package br.com.texsistemas.transita.presentation.ui.common.mock

import br.com.texsistemas.transita.domain.model.Endereco
import br.com.texsistemas.transita.domain.model.Horario
import br.com.texsistemas.transita.domain.model.HorarioVeiculo
import br.com.texsistemas.transita.domain.model.InformacoesVeiculo
import br.com.texsistemas.transita.domain.model.PontoOnibus
import br.com.texsistemas.transita.domain.model.RotaVeiculoPonto
import br.com.texsistemas.transita.domain.model.Veiculo

val pontoOnibusMock = PontoOnibus(
    id = "1",
    titulo = "Santa",
    informacao = "A parada de ônibus localizada nas proximidades da Escola Manoel Pereira Lins, atende a vários alunos do bairro alto da conceição.",
    descricao = "Local de fácil acesso para pegar o ônibus nos bairros próximos.",
//    endereco = "Av. Afonso Magalhães, 674 - São Cristovao, Serra Talhada - PE, 56903-040",
    endereco = Endereco(
        rua = "Av. Afonso Magalhães",
        numero = "674",
        bairro = "São Cristovão",
        cidade = "Serra Talhada",
        uf = "PE",
        cep = "56903-040"
    ),
    avaliacao = 4.5f,
    latitude = -23.5505,
    longitude = -46.6333,
    horarios = listOf(
        Horario(id = "1", horario = "08:00", tipoPonto = "EMBARQUE"),
        Horario(id = "2", horario = "09:00", tipoPonto = "DESEMBARQUE"),
        Horario(id = "3", horario = "10:00", tipoPonto = "EMBARQUE"),
        Horario(id = "4", horario = "11:00", tipoPonto = "DESEMBARQUE"),
        Horario(id = "5", horario = "12:00", tipoPonto = "EMBARQUE"),
        Horario(id = "6", horario = "13:00", tipoPonto = "DESEMBARQUE"),
        Horario(id = "7", horario = "14:00", tipoPonto = "EMBARQUE"),
        Horario(id = "8", horario = "15:00", tipoPonto = "DESEMBARQUE"),
        Horario(id = "9", horario = "16:00", tipoPonto = "EMBARQUE"),
        Horario(id = "10", horario = "17:00", tipoPonto = "DESEMBARQUE"),
        Horario(id = "11", horario = "18:00", tipoPonto = "EMBARQUE")
    ),
    tipoPonto = "AMBOS",
    linkImagem = null
)

val veiculoMock = Veiculo(
    id = "1",
    titulo = "Ônibus Escolar",
    placa = "ABC-1234",
    tipoTransporte = "Escolar",
    capacidade = 40,
    ativo = true,
    dataCriacao = "2024-06-01T08:00:00Z",
    informacoesVeiculo = InformacoesVeiculo(
        latitude = -7.988681,
        longitude = -38.297155,
        dataRegistro = "2024-06-01T08:00:00Z",
        dataRegistroProxPonto = "2024-06-01T08:15:00Z",
        quantidadePassageiros = 25,
        horarioAtualId = "1"
    ),
    horarios = listOf(
        HorarioVeiculo(
            id = "1",
            horarioId = "08:00"
        ),
        HorarioVeiculo(
            id = "2",
            horarioId = "09:00"
        )
    ),
    rotaVeiculoPonto = listOf(
        RotaVeiculoPonto(
            veiculoId = "1",
            horarioId = "1",
            pontoId = "1",
            ordem = 1
        ),
        RotaVeiculoPonto(
            veiculoId = "1",
            horarioId = "1",
            pontoId = "2",
            ordem = 2
        )
    )
)