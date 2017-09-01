package com.github.hteph.Utilities;

import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.transform.Scale;

public class PrintThisPage {
	
    public static void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        
        double originalX = node.getBoundsInParent().getWidth();
        double originalY = node.getBoundsInParent().getHeight();
        double scaleX = pageLayout.getPrintableWidth() / originalX;
        double scaleY = pageLayout.getPrintableHeight() / originalY;
        node.getTransforms().add(new Scale(scaleX, scaleY));
 
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
                node.getTransforms().add(new Scale(1/scaleX, 1/scaleY));
            }
        }
    }

}
