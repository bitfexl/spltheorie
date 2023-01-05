package com.github.bitfexl.splparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.IOException;
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

    public static void main(String[] args) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (String pdf : PDF_NAMES) {
            try (PDDocument doc = PDDocument.load(Main.class.getResourceAsStream("/pdf/" + pdf))) {

                String text = new PDFTextStripper().getText(doc);
                List<Question> parsedQuestions = new SPLParser().parse(text);
                String json = gson.toJson(parsedQuestions);

                System.out.println("Questions in PDF\n---------------------------------");
                // System.out.println(text);
                // System.out.println(json);
                System.out.println("Size: " + parsedQuestions.size());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}