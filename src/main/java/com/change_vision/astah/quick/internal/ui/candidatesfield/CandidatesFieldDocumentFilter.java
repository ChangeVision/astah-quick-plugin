package com.change_vision.astah.quick.internal.ui.candidatesfield;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class CandidatesFieldDocumentFilter extends DocumentFilter {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesFieldDocumentFilter.class);

    @Override
    public void replace(FilterBypass fb, int offset, int length,
            String text, AttributeSet attrSet) throws BadLocationException {
        if ( isInputConversion(attrSet)){
            logger.trace("Input Conversion");
            return;
        }
        super.replace(fb, offset, length, text, attrSet);
    }

    private boolean isInputConversion(AttributeSet attrSet) {
        return attrSet != null 
                && attrSet.isDefined(StyleConstants.ComposedTextAttribute);
    }
}
