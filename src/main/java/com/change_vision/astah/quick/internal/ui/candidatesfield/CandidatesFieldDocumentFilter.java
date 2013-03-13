package com.change_vision.astah.quick.internal.ui.candidatesfield;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleConstants;

final class CandidatesFieldDocumentFilter extends DocumentFilter {
    @Override
    public void replace(FilterBypass fb, int offset, int length,
            String text, AttributeSet attrSet) throws BadLocationException {
        if ( isInputConversion(attrSet)){
            return;
        }
        super.replace(fb, offset, length, text, attrSet);
    }

    private boolean isInputConversion(AttributeSet attrSet) {
        return attrSet != null 
                && attrSet.isDefined(StyleConstants.ComposedTextAttribute);
    }
}
