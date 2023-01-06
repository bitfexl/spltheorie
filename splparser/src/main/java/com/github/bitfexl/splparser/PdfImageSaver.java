package com.github.bitfexl.splparser;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Extract images from pdf.
 * see: <a href="https://www.tutorialkart.com/pdfbox/extract-images-from-pdf-using-pdfbox/">https://www.tutorialkart.com/pdfbox/extract-images-from-pdf-using-pdfbox/</a>
 */
public class PdfImageSaver extends PDFStreamEngine {
    public record PdfImage(int page, BufferedImage image) { }

    private List<PdfImage> images;

    private int currentPage;

    /**
     * Get all the images in the order they appear.
     * @param document The document to save images from.
     * @return A list of images and the page they appear on.
     * @throws IOException Error parsing pdf document.
     */
    public List<PdfImage> getImages(PDDocument document) throws IOException {
        images = new ArrayList<>();
        currentPage = 0;

        for (PDPage page : document.getPages()) {
            processPage(page);
            currentPage++;
        }

        return images;
    }

    @Override
    protected void processOperator( Operator operator, List<COSBase> operands) throws IOException {
        if ("Do".equals(operator.getName())) {
            COSName objectName = (COSName) operands.get(0);
            PDXObject xobject = getResources().getXObject(objectName);

            if (xobject instanceof PDImageXObject image) {
                images.add(new PdfImage(currentPage, image.getImage()));
            }
        } else {
            super.processOperator(operator, operands);
        }
    }
}
