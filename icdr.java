import com.aspose.imaging.Image;
import com.aspose.imaging.ImageResizeSettings;
import com.aspose.imaging.ResizeType;
import com.aspose.imaging.imagefilters.filteroptions.FilterOptionsBase;
import com.aspose.imaging.imageoptions.*;

import java.io.File;
import java.util.Locale;
import java.util.Random;

public class icdr {

    public static final String src = "src/";
    public static final String res = "res/";
    public static final String tmp = "temp/";

    public static void main(String[] args){
        Locale locale = new Locale("en", "US");
        Locale.setDefault(locale);

        com.aspose.imaging.License lici = new com.aspose.imaging.License();
        try {
            lici.setLicense(icdr.class.getClassLoader().getResourceAsStream("Aspose.Total.Product.Family.lic"));
        }
        catch (Exception exception){
            exception.printStackTrace();
        }

        int fail = 0;
        File dir = new File(src);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File f : directoryListing) {
                try{
                    String path = f.getAbsolutePath();
                    String name = f.getName().split("\\.")[0];
                    //transCode(path, name);
                    //resize(path, name);
                    //clean(path, name);
                    //AdvFilter(path ,name);
                    //final_fun(path, name);
                    //alpha(path, name);
                }
                catch (Exception e){
                    e.printStackTrace();
                    fail++;
                }
            }
        }
        System.out.println(fail);
    }

    public static void transCode(String path, String name){
        Image img = Image.load(path);
        img.save(res+name+"_trn.jpg", new JpegOptions());
        img.close();
    }

    public static void clean(String path, String name){
        com.aspose.imaging.RasterImage img = (com.aspose.imaging.RasterImage) Image.load(path);
        Random rd = new Random();
        byte mask = ~7;
        if(img.getWidth() * img.getHeight() > 30000000){
            throw new RuntimeException("out of bounds image!");
        }
        com.aspose.imaging.Color[] pixels = img.loadPixels(img.getBounds());
        for (int index = 0; index < pixels.length; index++) {
            byte A = pixels[index].getA();
            byte R = (byte) (pixels[index].getR() & mask + rd.nextInt(8));
            byte G = (byte) (pixels[index].getG() & mask + rd.nextInt(8));
            byte B = (byte) (pixels[index].getB() & mask + rd.nextInt(8));
            pixels[index] = pixels[index].fromArgb(A,R,G,B);
        }
        img.savePixels(img.getBounds(), pixels);
        img.save(res+name+"_lsb.jpg", new JpegOptions());
        img.close();
    }

    public static void resize(String path, String name){
        Image img = Image.load(path);
        float scale = 0.97f;
        int w = img.getWidth();
        int h = img.getHeight();
        img.resize((int)(w * scale), (int)(h* scale));
        img.resize(w, h);
        img.save(res+name+"_size97.jpg", new JpegOptions());
        img.close();
    }

    public static void alpha(String path, String name){ //image detox 
        com.aspose.imaging.RasterImage img = (com.aspose.imaging.RasterImage) Image.load(path);
        double g = 1.015;
        if(img.getWidth() * img.getHeight() > 30000000){
            throw new RuntimeException("out of bounds image!");
        }
        com.aspose.imaging.Color[] pixels = img.loadPixels(img.getBounds());
        for (int index = 0; index < pixels.length; index++) {
            byte A = pixels[index].getA();
            byte R = (byte) (Math.round(Math.pow(pixels[index].getR() & 0xFF,1/g)));
            byte G = (byte) (Math.round(Math.pow(pixels[index].getG() & 0xFF,1/g)));
            byte B = (byte) (Math.round(Math.pow(pixels[index].getB() & 0xFF,1/g)));
            pixels[index] = pixels[index].fromArgb(A,R,G,B);
        }
        img.savePixels(img.getBounds(), pixels);
        img.save(res+name+"_alp.jpg", new JpegOptions());
        img.close();
    }
        //String folder = "all_msb/";
        com.aspose.imaging.RasterImage img = (com.aspose.imaging.RasterImage) Image.load(path);
        Random rd = new Random();
        double g = rd.nextDouble() * 0.02 + 1.01;
        double a = rd.nextDouble() * 0.05 + 1.0;
        if(img.getWidth() * img.getHeight() > 10000000){
            throw new RuntimeException("out of bounds image!");
        }
        com.aspose.imaging.Color[] pixels = img.loadPixels(img.getBounds());
        for (int index = 0; index < pixels.length; index++) {
            byte A = pixels[index].getA();
            byte R = (byte) (Math.round(Math.pow(pixels[index].getR() & 0xFF,1/g)));
            byte G = (byte) (Math.round(Math.pow(pixels[index].getG() & 0xFF,1/g)));
            byte B = (byte) (Math.round(Math.pow(pixels[index].getB() & 0xFF,1/g)));
            pixels[index] = pixels[index].fromArgb(A,R,G,B);
        }
        img.savePixels(img.getBounds(), pixels);
        img.filter(img.getBounds(), new com.aspose.imaging.imagefilters.filteroptions.GaussianBlurFilterOptions());
        img.filter(img.getBounds(), new com.aspose.imaging.imagefilters.filteroptions.SharpenFilterOptions());
        for (int index = 0; index < pixels.length; index++) {
            byte A = pixels[index].getA();
            byte R = (byte) (Math.round((pixels[index].getR() & 0xFF) * a));
            byte G = (byte) (Math.round((pixels[index].getG() & 0xFF) * a));
            byte B = (byte) (Math.round((pixels[index].getB() & 0xFF) * a));
            pixels[index] = pixels[index].fromArgb(A,R,G,B);
        }
        img.savePixels(img.getBounds(), pixels);
        float scale = 0.95f;
        int w = img.getWidth();
        int h = img.getHeight();
        img.resize((int)(w * scale), (int)(h* scale));
        img.resize(w, h);
        img.save(res+name+"_all.png", new PngOptions());
        img.close();
    }

    public static void AdvFilter(String path, String name){
        com.aspose.imaging.RasterImage img = (com.aspose.imaging.RasterImage) Image.load(path);
        img.filter(img.getBounds(), new com.aspose.imaging.imagefilters.filteroptions.GaussianBlurFilterOptions());
        img.filter(img.getBounds(), new com.aspose.imaging.imagefilters.filteroptions.SharpenFilterOptions());
        img.save(res+name+"_fil.jpg", new JpegOptions());
        img.close();
    }

    public static void filter_types(String path, String name){  //test effects of up to 6 layers of filters
        FilterOptionsBase[] filter = {new com.aspose.imaging.imagefilters.filteroptions.BilateralSmoothingFilterOptions(),
                new com.aspose.imaging.imagefilters.filteroptions.GaussianBlurFilterOptions(),
                new com.aspose.imaging.imagefilters.filteroptions.MedianFilterOptions(5),
                new com.aspose.imaging.imagefilters.filteroptions.GaussWienerFilterOptions(),
                new com.aspose.imaging.imagefilters.filteroptions.SharpenFilterOptions()};
        String[] end = {"bsf","gbf","mf","gwf","sf"};
        for(int i = 1; i < 15625; i++){
            int j = i;
            com.aspose.imaging.RasterImage img = (com.aspose.imaging.RasterImage) Image.load(path);
            String e = "";
            while(j != 0){
                int type = j % 5;
                j -= type;
                j /= 5;
                img.filter(img.getBounds(), filter[type]);
                e += end[type];
                if(j != 0) e += "+";
            }
            System.out.println(e);
            img.save(res+name+"_"+e+".jpg", new JpegOptions());
        }
    }

    public static void final_fun(String path, String name){  //ICDR final method
        Image img1 = Image.load(path);
        img1.save(tmp+"tmp.png", new PngOptions());
        img1.close();
        com.aspose.imaging.RasterImage img = (com.aspose.imaging.RasterImage) Image.load(tmp+"tmp.png");
        if(img.getWidth() * img.getHeight() > 30000000){
            throw new RuntimeException("out of bounds image!");
        }
        float scale = 0.95f;
        int w = img.getWidth();
        int h = img.getHeight();
        img.resize((int)(w * scale), (int)(h* scale));
        img.resize(w, h);
        img.filter(img.getBounds(), new com.aspose.imaging.imagefilters.filteroptions.GaussianBlurFilterOptions());
        img.filter(img.getBounds(), new com.aspose.imaging.imagefilters.filteroptions.SharpenFilterOptions());
        img.save(res+name+"_fin.jpg", new JpegOptions());
        img.close();
    }

}
