package com.huawei.works.videolive.textstyle;

import android.graphics.Bitmap;

import java.io.InputStream;
import java.util.Vector;

/**
 * @author mao
 * @date 18/1/18
 */
public class GifHelper {
    public static final int STATUS_OK = 0;
    public static final int STATUS_FORMAT_ERROR = 1;
    public static final int STATUS_OPEN_ERROR = 2;
    protected int status;
    protected InputStream in;
    protected int width;
    protected int height;
    protected boolean gctFlag;
    protected int gctSize;
    protected int loopCount = 1;
    protected int[] gct;
    protected int[] lct;
    protected int[] act;
    protected int bgIndex;
    protected int bgColor;
    protected int lastBgColor;
    protected int pixelAspect;
    protected boolean lctFlag;
    protected boolean interlace;
    protected int lctSize;
    protected int ix;
    protected int iy;
    protected int iw;
    protected int ih;
    protected int lrx;
    protected int lry;
    protected int lrw;
    protected int lrh;
    protected Bitmap image;
    protected Bitmap lastImage;
    protected int frameindex = 0;
    protected byte[] block = new byte[256];
    protected int blockSize = 0;
    protected int dispose = 0;
    protected int lastDispose = 0;
    protected boolean transparency = false;
    protected int delay = 0;
    protected int transIndex;
    protected static final int MaxStackSize = 4096;
    protected short[] prefix;
    protected byte[] suffix;
    protected byte[] pixelStack;
    protected byte[] pixels;
    protected Vector<GifFrame> frames;
    protected int frameCount;

    public GifHelper() {
    }

    public int getFrameindex() {
        return frameindex;
    }

    public void setFrameindex(int frameindex) {
        frameindex = frameindex;
        if (frameindex > frames.size() - 1) {
            boolean var2 = false;
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeigh() {
        return height;
    }

    public int getDelay(int n) {
        delay = -1;
        if (n >= 0 && n < frameCount) {
            delay = ((GifHelper.GifFrame) frames.elementAt(n)).delay;
        }

        return delay;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public Bitmap getImage() {
        return getFrame(0);
    }

    public int getLoopCount() {
        return loopCount;
    }

    protected void setPixels() {
        int[] dest = new int[width * height];
        int pass;
        int iline;
        int i;
        int line;
        if (lastDispose > 0) {
            if (lastDispose == 3) {
                pass = frameCount - 2;
                if (pass > 0) {
                    lastImage = getFrame(pass - 1);
                } else {
                    lastImage = null;
                }
            }

            if (lastImage != null) {
                lastImage.getPixels(dest, 0, width, 0, 0, width, height);
                if (lastDispose == 2) {
                    pass = 0;
                    if (!transparency) {
                        pass = lastBgColor;
                    }

                    for (int var131 = 0; var131 < lrh; ++var131) {
                        iline = (lry + var131) * width + lrx;
                        i = iline + lrw;

                        for (line = iline; line < i; ++line) {
                            dest[line] = pass;
                        }
                    }
                }
            }
        }

        pass = 1;
        byte var13 = 8;
        iline = 0;

        for (i = 0; i < ih; ++i) {
            line = i;
            if (interlace) {
                if (iline >= ih) {
                    ++pass;
                    switch (pass) {
                        case 2:
                            iline = 4;
                            break;
                        case 3:
                            iline = 2;
                            var13 = 4;
                            break;
                        case 4:
                            iline = 1;
                            var13 = 2;
                    }
                }

                line = iline;
                iline += var13;
            }

            line += iy;
            if (line < height) {
                int k = line * width;
                int dx = k + ix;
                int dlim = dx + iw;
                if (k + width < dlim) {
                    dlim = k + width;
                }

                for (int sx = i * iw; dx < dlim; ++dx) {
                    int index = pixels[sx++] & 255;
                    int c = act[index];
                    if (c != 0) {
                        dest[dx] = c;
                    }
                }
            }
        }

        image = Bitmap.createBitmap(dest, width, height, Bitmap.Config.ARGB_4444);
    }

    public Bitmap getFrame(int n) {
        Bitmap im = null;
        if (n >= 0 && n < frameCount) {
            im = ((GifHelper.GifFrame) frames.elementAt(n)).image;
        }

        return im;
    }

    public Bitmap nextBitmap() {
        ++frameindex;
        if (frameindex > frames.size() - 1) {
            frameindex = 0;
        }

        return ((GifHelper.GifFrame) frames.elementAt(frameindex)).image;
    }

    public int nextDelay() {
        return ((GifHelper.GifFrame) frames.elementAt(frameindex)).delay;
    }

    public int read(InputStream is) {
        init();
        if (is != null) {
            in = is;
            readHeader();
            if (!err()) {
                readContents();
                if (frameCount < 0) {
                    status = 1;
                }
            }
        } else {
            status = 2;
        }

        try {
            is.close();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return status;
    }

    protected void decodeImageData() {
        byte NullCode = -1;
        int npix = iw * ih;
        if (pixels == null || pixels.length < npix) {
            pixels = new byte[npix];
        }

        if (prefix == null) {
            prefix = new short[4096];
        }

        if (suffix == null) {
            suffix = new byte[4096];
        }

        if (pixelStack == null) {
            pixelStack = new byte[4097];
        }

        int data_size = read();
        int clear = 1 << data_size;
        int end_of_information = clear + 1;
        int available = clear + 2;
        int old_code = NullCode;
        int code_size = data_size + 1;
        int code_mask = (1 << code_size) - 1;

        int code;
        for (code = 0; code < clear; ++code) {
            prefix[code] = 0;
            suffix[code] = (byte) code;
        }

        int bi = 0;
        int pi = 0;
        int top = 0;
        int first = 0;
        int count = 0;
        int bits = 0;
        int datum = 0;
        int i = 0;

        while (i < npix) {
            if (top == 0) {
                if (bits < code_size) {
                    if (count == 0) {
                        count = readBlock();
                        if (count <= 0) {
                            break;
                        }

                        bi = 0;
                    }

                    datum += (block[bi] & 255) << bits;
                    bits += 8;
                    ++bi;
                    --count;
                    continue;
                }

                code = datum & code_mask;
                datum >>= code_size;
                bits -= code_size;
                if (code > available || code == end_of_information) {
                    break;
                }

                if (code == clear) {
                    code_size = data_size + 1;
                    code_mask = (1 << code_size) - 1;
                    available = clear + 2;
                    old_code = NullCode;
                    continue;
                }

                if (old_code == NullCode) {
                    pixelStack[top++] = suffix[code];
                    old_code = code;
                    first = code;
                    continue;
                }

                int in_code = code;
                if (code == available) {
                    pixelStack[top++] = (byte) first;
                    code = old_code;
                }

                while (code > clear) {
                    pixelStack[top++] = suffix[code];
                    code = prefix[code];
                }

                first = suffix[code] & 255;
                if (available >= 4096) {
                    break;
                }

                pixelStack[top++] = (byte) first;
                prefix[available] = (short) old_code;
                suffix[available] = (byte) first;
                ++available;
                if ((available & code_mask) == 0 && available < 4096) {
                    ++code_size;
                    code_mask += available;
                }

                old_code = in_code;
            }

            --top;
            pixels[pi++] = pixelStack[top];
            ++i;
        }

        for (i = pi; i < npix; ++i) {
            pixels[i] = 0;
        }

    }

    protected boolean err() {
        return status != 0;
    }

    public void init() {
        status = 0;
        frameCount = 0;
        frames = new Vector();
        gct = null;
        lct = null;
    }

    protected int read() {
        int curByte = 0;

        try {
            curByte = in.read();
        } catch (Exception var3) {
            status = 1;
        }

        return curByte;
    }

    protected int readBlock() {
        blockSize = read();
        int n = 0;
        if (blockSize > 0) {
            int e1;
            try {
                for (boolean var4 = false; n < blockSize; n += e1) {
                    e1 = in.read(block, n, blockSize - n);
                    if (e1 == -1) {
                        break;
                    }
                }
            } catch (Exception var41) {
                var41.printStackTrace();
            }

            if (n < blockSize) {
                status = 1;
            }
        }

        return n;
    }

    protected int[] readColorTable(int ncolors) {
        int nbytes = 3 * ncolors;
        int[] tab = null;
        byte[] c = new byte[nbytes];
        int n = 0;

        try {
            n = in.read(c);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        if (n < nbytes) {
            status = 1;
        } else {
            tab = new int[256];
            int i = 0;

            int r;
            int g;
            int b;
            for (int j = 0; i < ncolors; tab[i++] = -16777216 | r << 16 | g << 8 | b) {
                r = c[j++] & 255;
                g = c[j++] & 255;
                b = c[j++] & 255;
            }
        }

        return tab;
    }

    protected void readContents() {
        boolean done = false;

        while (!done && !err()) {
            int code = read();
            switch (code) {
                case 0:
                    break;
                case 33:
                    code = read();
                    switch (code) {
                        case 249:
                            readGraphicControlExt();
                            continue;
                        case 255:
                            readBlock();
                            String app = "";

                            for (int i = 0; i < 11; ++i) {
                                app = app + (char) block[i];
                            }

                            if (app.equals("NETSCAPE2.0")) {
                                readNetscapeExt();
                            } else {
                                skip();
                            }
                            continue;
                        default:
                            skip();
                            continue;
                    }
                case 44:
                    readImage();
                    break;
                case 59:
                    done = true;
                    break;
                default:
                    status = 1;
            }
        }

    }

    protected void readGraphicControlExt() {
        read();
        int packed = read();
        dispose = (packed & 28) >> 2;
        if (dispose == 0) {
            dispose = 1;
        }

        transparency = (packed & 1) != 0;
        delay = readShort() * 10;
        transIndex = read();
        read();
    }

    protected void readHeader() {
        StringBuffer id = new StringBuffer();

        for (int i = 0; i < 6; ++i) {
            id.append((char) read());
        }

        if (!id.toString().startsWith("GIF")) {
            status = 1;
        } else {
            readLSD();
            if (gctFlag && !err()) {
                gct = readColorTable(gctSize);
                bgColor = gct[bgIndex];
            }
        }

    }

    protected void readImage() {
        ix = readShort();
        iy = readShort();
        iw = readShort();
        ih = readShort();
        int packed = read();
        lctFlag = (packed & 128) != 0;
        interlace = (packed & 64) != 0;
        lctSize = 2 << (packed & 7);
        if (lctFlag) {
            lct = readColorTable(lctSize);
            act = lct;
        } else {
            act = gct;
            if (bgIndex == transIndex) {
                bgColor = 0;
            }
        }

        int save = 0;
        if (transparency) {
            save = act[transIndex];
            act[transIndex] = 0;
        }

        if (act == null) {
            status = 1;
        }

        if (!err()) {
            decodeImageData();
            skip();
            if (!err()) {
                ++frameCount;
                image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                setPixels();
                frames.addElement(new GifHelper.GifFrame(image, delay));
                if (transparency) {
                    act[transIndex] = save;
                }

                resetFrame();
            }
        }

    }

    protected void readLSD() {
        width = readShort();
        height = readShort();
        int packed = read();
        gctFlag = (packed & 128) != 0;
        gctSize = 2 << (packed & 7);
        bgIndex = read();
        pixelAspect = read();
    }

    protected void readNetscapeExt() {
        do {
            readBlock();
            if (block[0] == 1) {
                int b1 = block[1] & 255;
                int b2 = block[2] & 255;
                loopCount = b2 << 8 | b1;
            }
        } while (blockSize > 0 && !err());

    }

    protected int readShort() {
        return read() | read() << 8;
    }

    protected void resetFrame() {
        lastDispose = dispose;
        lrx = ix;
        lry = iy;
        lrw = iw;
        lrh = ih;
        lastImage = image;
        lastBgColor = bgColor;
        dispose = 0;
        transparency = false;
        delay = 0;
        lct = null;
    }

    protected void skip() {
        do {
            readBlock();
        } while (blockSize > 0 && !err());

    }

    private class GifFrame {
        public Bitmap image;
        public int delay;

        public GifFrame(Bitmap im, int del) {
            image = im;
            delay = del;
        }
    }
}
