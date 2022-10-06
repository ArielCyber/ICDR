# ICDR
Image (JPEG) Content Disarm and Reconstruction

Contains the scripts used for Image Content Disarm and Recostruct, along with scripts used for testing and measuring results.
The aim of ICDR is to remove any potential threats (steganography and embedded malware) inside JPEG files and create a clean copy with minimal changes to the image quality.

To run the files and scripts you must have access to the Aspose.imaging library and a valid VirusTotal key, along with numpy, swear, pandas, vtapi3 python libraries.

## Files
icdr.java: contains the code tested for disarming potentialy malicious image files.
* transCode(): creates a new image file with the same image but with no metadata.
* clean(): edit by a random value the 3 least significant bytes of the every pixel of the image.
* resize(): compress the image to a 0.97 size of the original and back to the original size.
* alpha(): applies the function of x^(1/1.015) on the image, x being a pixel value of the image.
* AdvFilter(): applies the filters Gaussian Blur followed by Sharpen Filter.
* final_fun(): the optimal ICDR function resulted from the tests. the function applies the functions of transCode(), resize() and AdvFilter() on the image file.

image_quality.py: used to measure the quality effects of the icdr functions on the tested images. The code compares the files in output_base as the original to the modifed images in output_base2.

vt_ider.py: used to upload hashes of files to VirusTotal and generate a csv file with the resulting ids' of the files uploaded.

vt_analyse.py: reads the .csv file generated from vt_ider.py and queries the results from VirusTotal as a .csv file.

## Results
