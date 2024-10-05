# Image-Transformation Application

## Introduction

The **Image Transformation Application** is a desktop application built using Java and JavaFX that allows users to perform various image processing operations. The application enables users to open an image, apply different transformations, and save the modified image. It supports common transformations such as grayscale conversion, inversion, brightness adjustment, saturation changes, and custom effects like red-gray scaling.

The project showcases the use of advanced Java features, including functional interfaces and lambda expressions, to implement efficient pixel-wise transformations. The user interface is designed using JavaFX FXML and SceneBuilder, following the Model-View-Controller (MVC) architectural pattern for better code organization and maintainability.

## Features

- Multiple Image Transformations:

    - Grayscale
    - Invert Colors 
    - Brighten 
    - Darken 
    - Desaturate 
    - Saturate 
    - Red Filter 
    - Red-Gray Effect 
    - Blue Filter 
    - Mirror Image 
    - Widen Image 

- User-Friendly Interface:

    - Intuitive GUI built with JavaFX FXML and SceneBuilder. 
    - Easy navigation and operation for applying transformations.

- Custom Image Formats:

    - Supports reading and writing custom image formats (.msoe, .bmsoe). 
    - Handles standard image formats like .png. 

- Efficient Image Processing:

    - Utilizes functional interfaces and lambda expressions for efficient pixel manipulation. 
    - Implements a generic transformImage() method to apply transformations.
  
- Error Handling:

    - Displays alerts for invalid inputs or file access issues.
    - Provides informative error messages to guide the user.

## Architecture and Design

### Model-View-Controller (MVC) Pattern
The application follows the MVC architectural pattern:

- Model: The core logic for image processing and I/O operations (ImageIO, Transformable).
- View: The FXML layout file (sceneBuilder.fxml) that defines the user interface.
- Controller: The ImageController class that handles user interactions, updates the view, and communicates with the model.

This separation enhances code modularity, maintainability, and scalability.

### Functional Interface and Lambda Expressions
- Transformable Interface: A functional interface that defines a method for applying a transformation to a pixel's color.
- Lambda Expressions: Used to implement various image transformations in a concise and efficient manner.
- Generic transformImage() Method: Applies any transformation passed as a Transformable to each pixel of the image.

## Installation and Setup

### Prerequisites
- Java Development Kit (JDK): Version 8 or higher.
- JavaFX SDK: Ensure JavaFX is included in your Java installation or available in your classpath.
- Integrated Development Environment (IDE): Such as IntelliJ IDEA, Eclipse, or NetBeans.

### Running the Application
1. Clone or Download the Repository:
    - git clone https://github.com/miziukv/Image-Transformer.git
2. Open the Project in Your IDE:

    - Import the project as a Java project.
    - Ensure that the JavaFX library is properly configured.

3. Build the Project:

    - Compile the source code. 
    - Resolve any dependencies or library references. 

4. Run the Application:

    - Run the ImageTransformer class, which contains the main method.

## Usage

### Opening an Image

1. Launch the Application.

2. Click the "Open" Button:

    - A file chooser dialog will appear. 
    - Navigate to the image file you wish to open. 
    - Supported formats: .png, .msoe, .bmsoe.

3. Image Display:

    - The selected image will be displayed in the application window.

### Applying Transformations

1. Select a Transformation:
    - Click any of the transformation buttons:
      - Grayscale, Invert, Brighten, Darken, Desaturate, Saturate, Red, Red-Gray, Blue, Mirror, Widen.

2. View the Result:

    - The image will update to show the effect of the transformation.

3. Chain Transformations:

    - You can apply multiple transformations sequentially.

### Saving the Image

1. Click the "Save" Button:

    - A file chooser dialog will appear.

2. Choose a Destination and Format:

    - Specify the file name and select the desired format (.png, .msoe, .bmsoe).

3. Save the Image:

    - The transformed image will be saved to the specified location.

*Note:* If no image is open when you click "Save", an error alert will notify you.

## Supported Image Formats

The application supports the following image formats:

- PNG (.png):
    - Standard image format. 
    - Supports lossless compression. 

- MSOE (.msoe):

  - Custom text-based image format. 
  - Stores image pixel data in hexadecimal color codes.

- Binary MSOE (.bmsoe):

    - Custom binary image format. 
    - Stores image pixel data in binary form for efficiency.

## Acknowledgments

- Dr. Taylor: For support and feedback throughout the development process.

Thank you for your interest in the Image Transformation Application!








