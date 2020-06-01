## Smart Xml Analyzer
This solution uses a scoring system, in which all possible target elements are evaluated against the original element and scored according to some criteria.  
It's easy to tweak the scoring parameters or to add new scoring functions.
* Scoring methods
    * Hierarchy level score  (_default max score = 0.15_)  
    Evaluates the similarity in the hierarchy of the elements using the following formula  
    score = 0.35 * e^(-x),  
     _x_ being the difference in levels between original and candidate element
    * Class list score  (_default max score = 0.35_)  
    Return 60% of max score if class list of candidate element contains at least one of the classes of the original element., 100% of max score if candidate element contains all the original element classes, and 0 otherwise.
    * Tag type score  (_default max score = 0.35_)  
    If the tags of the original and candidate elements are of same type returns full score. Returns 0 otherwise.
    * Inner text score (_default max score = 0.15_)  
    If elements have the same inner text, returns full score. Returns 0 otherwise  
      * TODO: implement some sort of text difference scoring algorithm
    * hRef attribute score  (_default max score = 0.15_)  
    If elements have same href attribute, returns full score. Returns 0 otherwise.    

### Usage
    Please note that log4j.xml should be located in same directory as the jar file
    ```
    java -jar xmlanalyzer.jar {id} {original_file} {diffcase_file}    
    ```
    Sample:
    ```
    java -jar xmlanalyzer.jar make-everything-ok-button ./samples/sample-0-origin.html ./samples/sample-1-evil-gemini.html
    ```



### Comparison output for sample pages
* **sample-1-evil-gemini.html**  
    ```
    Winner tag:  a 
    text:Make everything OK 
    classes: btn btn-success
    Score: 1.0
    Path: html>body>div>div>div[3]>div[1]>div>div[2]>a[2]
    ```
* **sample-2-container-and-clone.html**  
    ```
    Winner tag:  a 
    text:Make everything OK 
    classes: btn test-link-ok
    Score: 0.9151
    Path: html>body>div>div>div[3]>div[1]>div>div[2]>div>a
    ```
* **sample-3-the-escape.html**  
    ```
    Winner tag:  a 
    text:Do anything perfect
    classes: btn btn-success
    Score: 1.0
    Path: html>body>div>div>div[3]>div[1]>div>div[3]>a
    ```
* **sample-4-the-mash.html**  
    ```
    Winner tag:  a 
    text::Do all GREAT
    classes: btn btn-success
    Score: 1.0
    Path: html>body>div>div>div[3]>div[1]>div>div[3]>a
    ```


