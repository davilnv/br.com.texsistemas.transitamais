package br.com.texsistemas.transitamais.pontoservice.domain.utils;

import br.com.texsistemas.transitamais.commons.api.dto.ponto.PontoOnibusDTO;

import java.util.List;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class GeoUtils {

    private static final BigDecimal RAIO_TERRA_KM = new BigDecimal("6371");
    private static final MathContext MC = new MathContext(15, RoundingMode.HALF_UP);

    public static BigDecimal calcularDistancia(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        BigDecimal lat1Rad = toRadians(lat1);
        BigDecimal lon1Rad = toRadians(lon1);
        BigDecimal lat2Rad = toRadians(lat2);
        BigDecimal lon2Rad = toRadians(lon2);

        BigDecimal dLat = lat2Rad.subtract(lat1Rad, MC);
        BigDecimal dLon = lon2Rad.subtract(lon1Rad, MC);

        BigDecimal sinDLat = sin(dLat.divide(BigDecimal.valueOf(2), MC));
        BigDecimal sinDLon = sin(dLon.divide(BigDecimal.valueOf(2), MC));

        BigDecimal a = sinDLat.pow(2, MC)
                .add(cos(lat1Rad).multiply(cos(lat2Rad), MC)
                        .multiply(sinDLon.pow(2, MC), MC), MC);

        BigDecimal c = BigDecimal.valueOf(2).multiply(
                BigDecimal.valueOf(Math.atan2(Math.sqrt(a.doubleValue()), Math.sqrt(1 - a.doubleValue()))), MC
        );

        return RAIO_TERRA_KM.multiply(c, MC);
    }

    private static BigDecimal toRadians(BigDecimal valor) {
        return BigDecimal.valueOf(Math.toRadians(valor.doubleValue()));
    }

    private static BigDecimal sin(BigDecimal valor) {
        return BigDecimal.valueOf(Math.sin(valor.doubleValue()));
    }

    private static BigDecimal cos(BigDecimal valor) {
        return BigDecimal.valueOf(Math.cos(valor.doubleValue()));
    }


    public static List<PontoOnibusDTO> filtrarPontosProximos(List<PontoOnibusDTO> pontos,
                                                             BigDecimal lat,
                                                             BigDecimal lon,
                                                             BigDecimal distanciaMaximaKm) {
        return pontos.stream()
                .filter(ponto -> calcularDistancia(lat, lon, ponto.latitude(), ponto.longitude())
                        .compareTo(distanciaMaximaKm) <= 0)
                .collect(Collectors.toList());
    }

}

