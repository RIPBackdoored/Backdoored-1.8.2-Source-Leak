package com.google.cloud.storage;

class BlobWriteChannel$1 implements Runnable {
    final /* synthetic */ int val$length;
    final /* synthetic */ boolean val$last;
    final /* synthetic */ BlobWriteChannel this$0;
    
    BlobWriteChannel$1(final BlobWriteChannel a1, final int val$length, final boolean val$last) {
        this.this$0 = a1;
        this.val$length = val$length;
        this.val$last = val$last;
        super();
    }
    
    @Override
    public void run() {
        ((StorageOptions)BlobWriteChannel.access$300(this.this$0)).getStorageRpcV1().write(BlobWriteChannel.access$000(this.this$0), BlobWriteChannel.access$100(this.this$0), 0, BlobWriteChannel.access$200(this.this$0), this.val$length, this.val$last);
    }
}