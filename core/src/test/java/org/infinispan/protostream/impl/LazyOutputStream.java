package org.infinispan.protostream.impl;

import java.io.OutputStream;
import java.util.Arrays;

import org.infinispan.protostream.LazyByteArrayOutputStream;

class LazyOutputStream extends OutputStream implements LazyByteArrayOutputStream {

   static final int DEFAULT_SIZE = 32;
   static final int DEFAULT_DOUBLING_SIZE = 4 * 1024 * 1024; // 4MB

   byte[] buf;
   int pos;

   public LazyOutputStream(int capacity) {
      this.buf = new byte[capacity];
   }

   @Override
   public void write(byte[] b, int off, int len) {
      if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
         throw new IndexOutOfBoundsException();
      } else if (len == 0) {
         return;
      }

      int newcount = pos + len;
      ensureCapacity(newcount);

      System.arraycopy(b, off, buf, pos, len);
      pos = newcount;
   }

   @Override
   public void write(int b) {
      int newcount = pos + 1;
      ensureCapacity(newcount);
      buf[pos] = (byte) b;
      pos = newcount;
   }

   @Override
   public void ensureCapacity(int newcount) {
      if (buf == null) {
         // Pretend we have half the default size so it's doubled
         buf = new byte[Math.max(DEFAULT_SIZE, newcount)];
      } else if (newcount > buf.length) {
         byte[] newbuf = new byte[getNewBufferSize(buf.length, newcount)];
         System.arraycopy(buf, 0, newbuf, 0, pos);
         buf = newbuf;
      }
   }

   private int getNewBufferSize(int curSize, int minNewSize) {
      if (curSize <= DEFAULT_DOUBLING_SIZE)
         return Math.max(curSize << 1, minNewSize);
      else
         return Math.max(curSize + (curSize >> 2), minNewSize);
   }

   @Override
   public int getPosition() {
      return pos;
   }

   @Override
   public void setPosition(int position) {
      this.pos = position;
   }

   @Override
   public byte[] getRawBuffer() {
      return buf;
   }

   byte[] getTrimmedBuffer() {
      byte[] buf = getRawBuffer();
      if (buf == null)
         return new byte[0];
      int pos = getPosition();
      if (buf.length == pos) {
         return buf;
      }
      return Arrays.copyOf(buf, pos);
   }
}
