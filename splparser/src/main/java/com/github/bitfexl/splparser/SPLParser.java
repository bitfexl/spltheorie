package com.github.bitfexl.splparser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPLParser {
    private static final String HAS_ANNEX = "Please pay attention to annex ";

    private static final String ANNEX_PAGE_REGEX = "Annexes\\s*\\d\\s*Annex \\d\\s*";

    private final SPLQuestionParser questionParser = new SPLQuestionParser();

    private final PdfImageSaver imageSaver = new PdfImageSaver();

    public List<Question> parsePdf(InputStream pdf) throws IOException {
        try (PDDocument doc = PDDocument.load(pdf)) {
            String text = new PDFTextStripper().getText(doc);
            List<Question> parsedQuestions = questionParser.parse(text);

            if (parsedQuestions.get(parsedQuestions.size() - 1).getQuestionIndex() != parsedQuestions.size()) {
                throw new IOException("Parsing error: Question count does not match.");
            }

            parseImages(parsedQuestions, doc);

            return parsedQuestions;
        }
    }

    private void parseImages(List<Question> questions, PDDocument doc) throws IOException {
        PDFTextStripper reader = new PDFTextStripper();

        // Annex id and image
        Map<Integer, BufferedImage> images = new HashMap<>();

        for (PdfImageSaver.PdfImage img : imageSaver.getImages(doc)) {
            reader.setStartPage(img.page() + 1);
            reader.setEndPage(img.page() + 1);
            String text = reader.getText(doc);

            if (text.matches(ANNEX_PAGE_REGEX)) {
                int index = Integer.parseInt(text.split("Annex ")[1].split(" ")[0]);
                images.put(index, img.image());
            }
        }

        for (Question question : questions) {
            if (question.getQuestion().contains(HAS_ANNEX)) {
                // Annex is always the last line
                String[] parts = question.getQuestion().split(HAS_ANNEX);
                int annexIndex = Integer.parseInt(parts[1]);

                BufferedImage annex = images.get(annexIndex);

                if (annex != null) {
                    String base64 = ImgUtils.toBase64(annex);
                    question.setImage(base64);
                } else {
                    System.out.println("WARNING: Annex " + annexIndex + " not found");
                }
            }
        }
    }
}
