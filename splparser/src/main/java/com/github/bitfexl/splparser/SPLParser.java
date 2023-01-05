package com.github.bitfexl.splparser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SPLParser {
    private static final String HAS_ANNEX = "Please pay attention to annex ";

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
        List<BufferedImage> images = imageSaver.getImages(doc);

        for (Question question : questions) {
            if (question.getQuestion().contains(HAS_ANNEX)) {
                // Annex is always the last line
                String[] parts = question.getQuestion().split(HAS_ANNEX);
                int annexIndex = Integer.parseInt(parts[1]);

                // First image is a banner, so annexIndex = index
                BufferedImage annex = images.get(annexIndex);
                String base64 = ImgUtils.toBase64(annex);

                question.setImage(base64);
            }
        }
    }
}
