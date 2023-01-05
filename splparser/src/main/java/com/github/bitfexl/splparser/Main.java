package com.github.bitfexl.splparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {
    private static final String[] PDF_NAMES = {
            "SPL-ECQB-PPL-10-ALW-ge.pdf",
            "SPL-ECQB-PPL-20-HPL-ge.pdf",
            "SPL-ECQB-PPL-30-MET-ge.pdf",
            "SPL-ECQB-PPL-40-COM-ge.pdf",
            "SPL-ECQB-PPL-51-PFA-ge.pdf",
            "SPL-ECQB-PPL-60-OPR-ge.pdf",
            "SPL-ECQB-PPL-70-FPP-ge.pdf",
            "SPL-ECQB-PPL-80-AGK-ge.pdf",
            "SPL-ECQB-PPL-90-NAV-ge.pdf"
    };

    /**
     * arg0 = output dir ending with '/'
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws IOException {
        final String outputDir = args.length > 0 ? args[0] : "./";
        new File(outputDir).mkdirs();

        final Gson gson = new GsonBuilder()/*.setPrettyPrinting()*/.create();

        final SPLParser parser = new SPLParser();

        for (String pdf : PDF_NAMES) {
            try (InputStream inputStream = Main.class.getResourceAsStream("/pdf/" + pdf)) {
                List<Question> questions = parser.parsePdf(inputStream);
                String json = gson.toJson(questions);

                try (FileWriter fileWriter = new FileWriter(outputDir + getId(pdf) + ".json")) {
                    fileWriter.write(json);
                }
            }
        }
    }

    private static String getId(String pdfName) {
        return pdfName.split("-")[4];
    }
}