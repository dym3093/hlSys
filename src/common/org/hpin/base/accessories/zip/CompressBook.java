package org.hpin.base.accessories.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.hpin.base.accessories.zipdownload.ZipOutputStream;


public class CompressBook {
  public CompressBook() {
  }

 
  public void zip(String inputFileName) throws Exception {
    String zipFileName = "D:\\ziptest\\test.zip";  
    System.out.println(zipFileName);
    zip(zipFileName, new File(inputFileName));
  }

  private void zip(String zipFileName, File inputFile) throws Exception {
    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
    zip(out, inputFile, "");
    System.out.println("zip done");
    out.close();
  }

  private void zip(ZipOutputStream out, File f, String base) throws Exception {
    if (f.isDirectory()) {
      File[] fl = f.listFiles();
      out.putNextEntry(new org.hpin.base.accessories.zipdownload.ZipEntry(base + "/"));
      base = base.length() == 0 ? "" : base + "/";
      for (int i = 0; i < fl.length; i++) {
        zip(out, fl[i], base + fl[i].getName());
      }
    }
    else {
      out.putNextEntry(new org.hpin.base.accessories.zipdownload.ZipEntry(base));
      FileInputStream in = new FileInputStream(f);
      int b;
      System.out.println(base);
      while ( (b = in.read()) != -1) {
        out.write(b);
      }
      in.close();
    }
  }

  public static void main(String [] temp){
    CompressBook book = new CompressBook();
    try {
      book.zip("D:\\ziptest");
    }
    catch (Exception ex) {
    }
  }
}


