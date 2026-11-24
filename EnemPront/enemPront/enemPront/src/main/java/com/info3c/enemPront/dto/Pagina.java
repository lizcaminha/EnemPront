package com.info3c.enemPront.dto;

import java.util.List;

public record Pagina<T>(List<T> itens, int numero, int totalPaginas, long total) {
}
