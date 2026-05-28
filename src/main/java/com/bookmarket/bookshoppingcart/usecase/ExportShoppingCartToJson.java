package com.bookmarket.bookshoppingcart.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ExportShoppingCartToJson {

    private final GetShoppingCartDetails getShoppingCartDetails;
    private final ObjectMapper objectMapper;

    public String export() throws IOException {
        var cart = getShoppingCartDetails.getCartDetails();

        var tempDirPath = Paths.get("c:/temp");
        if (!Files.exists(tempDirPath)) {
            Files.createDirectories(tempDirPath);
        }

        var outputFile = new File(tempDirPath.toFile(), "shopping_cart.json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, cart);

        return outputFile.getAbsolutePath();
    }
}