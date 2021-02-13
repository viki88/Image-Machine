package com.vikination.imagemachine.ui.detail;

import com.vikination.imagemachine.model.Image;

public interface OnThumbnailClickListener {
    void onClickThumbnail(String uri);
    void onLongClickThumbnail(Image image);
}
