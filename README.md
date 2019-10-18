# lpr
LPR

Abstract

This application is part of the master's thesis aims to compare the methods to recognize car registration numbers on video recordings. These methods, which are machine replication of human reading, i.e. optical character recognition, have been the object of intense research for over three decades.
The indirect goal of the work is to build a tool that will allow comparing selected methods to recognize license plates on video recordings, depending on the following parameters: weather conditions, speed, lighting. The LBP methods and the morphological method will be compared. Considering this comparison, it is possible to choose the method that best deals with the recognition of registration numbers in different weather conditions, at any speed and in different lighting conditions, which is the main goal of the thesis.
In order to implement the mentioned tool, ie the application called "License Plate Recognition Race", technologies, tools, and libraries were used, such as OpenCV, Leptonica, Tesseract, OpenALPR, Java, video recorder and laptop.
In order to compare each of the tested methods, an original measure vector was created, consisting of factors related to the accuracy, efficiency, and processing time. These coefficients were calculated for each of the cases studied. This allowed evaluating these methods taking into account their accuracy, efficiency, and speed of their operation.
The morphological method proved to be the fastest of all methods. The accuracy of this method was also the highest - in each case it reached values ​​greater than in other methods - LBP CPU and LBP GPU. It follows that it can be considered as the most accurate. However, the efficiency of the morphological method leaves much to be desired - in each case, it was lower than in the other two methods. Therefore, it can be considered the least efficient.
Such an analysis allowed to define the most common errors and problems in the operation of each method and to designate further development proposals.
