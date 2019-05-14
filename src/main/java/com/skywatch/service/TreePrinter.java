package com.skywatch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Binary tree printer, modified to use logger by Ilman.
 *
 * @author MightyPork
 * @author Ilman
 */
public class TreePrinter {

    private static final Logger logger = LoggerFactory.getLogger(TreePrinter.class);

    /**
     * Node that can be printed
     */
    public interface PrintableNode {
        /**
         * Get left child
         */
        PrintableNode getLeft();


        /**
         * Get right child
         */
        PrintableNode getRight();


        /**
         * Get text to be printed
         */
        String getText();
    }


    /**
     * Print a tree
     *
     * @param root tree root node
     */
    public static void print(PrintableNode root) {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<PrintableNode> level = new ArrayList<PrintableNode>();
        List<PrintableNode> next = new ArrayList<PrintableNode>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getText();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        StringBuilder stringBuilder = new StringBuilder("Tree visualization:\n");
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    stringBuilder.append(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            stringBuilder.append(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            stringBuilder.append(j % 2 == 0 ? " " : "─");
                        }
                        stringBuilder.append(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            stringBuilder.append(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                stringBuilder.append("\n");
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(f);
                for (int k = 0; k < gap2; k++) {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");

            perpiece /= 2;
        }
        logger.info(stringBuilder.toString());
    }
}
