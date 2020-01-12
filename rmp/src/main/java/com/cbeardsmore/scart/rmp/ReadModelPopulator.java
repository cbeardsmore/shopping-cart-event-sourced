package com.cbeardsmore.scart.rmp;

import com.cbeardsmore.scart.rmp.persistence.Bookmark;
import com.cbeardsmore.scart.rmp.persistence.EventEnvelope;

public class ReadModelPopulator {

    private final Bookmark bookmark;

    public ReadModelPopulator(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    public void dispatch(EventEnvelope envelope) {
        bookmark.put(envelope.getPosition());
    }
}
