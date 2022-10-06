# ICDR
Image (JPEG) Content Disarm and Reconstruction

Contains the scripts used for Image Content Disarm and Recostruct, along with scripts used for testing and measuring results.
The aim of ICDR is to remove any potential threats (steganography and embedded malware) inside JPEG files and create a clean copy with minimal changes to the image quality.

To run the files and scripts you must have access to the Aspose.imaging library and a valid VirusTotal key, along with numpy, swear, pandas, vtapi3 python libraries.

## Files
icdr.java: contains the code tested for disarming potentialy malicious image files.
* transCode(): creates a new image file with the same image but with no metadata.
* clean(): edit by a random value, the 3 least significant bytes of the every pixel of the image.
* resize(): compress the image to a 0.97 size of the original and back to the original size.
* alpha(): applies the function of x^(1/1.015) on the image, x being a pixel value of the image. Based on [ImageDetox](https://www.mdpi.com/2073-8994/12/10/1621).
* AdvFilter(): applies the filters Gaussian Blur followed by Sharpen Filter.
* final_fun(): the optimal ICDR function resulted from the tests. the function applies the functions of transCode(), resize() and AdvFilter() on the image file.

image_quality.py: used to measure the quality effects of the icdr functions on the tested images. The code compares the files in output_base as the original to the modifed images in output_base2.

vt_ider.py: used to upload hashes of files to VirusTotal and generate a csv file with the resulting ids' of the files uploaded.

vt_analyse.py: reads the .csv file generated from vt_ider.py and queries the results from VirusTotal as a .csv file.

## Results
ICDR was tested on a dataset of JPEGs with membedded malware from VirusTotal and benign images that were used as a cover images for steganography from [Kaggle](https://www.kaggle.com/datasets/prasunroy/natural-images), along with [naive anti resize steganography](https://github.com/eloblo/naive-anti-resize-steganography).

The steganographic tools used for testing are: [OpenStego](https://github.com/syvaidya/openstego), [DCT-Image-Steganography](https://github.com/MasonEdgar/DCT-Image-Steganography), and a modified [LsbSteg](https://github.com/adrg/lsbsteg) that embed data in the 3 most significant bits of a pixel.

![1](/assets/images/quality_test.PNG)

![2](/assets/images/filters.PNG)

![3](/assets/images/res.PNG)

### Threat composition of VirusTotal dataset
compostion brought by [MalJpeg](https://ieeexplore.ieee.org/document/8967109)

![4](/assets/images/threats_cmp.PNG)

