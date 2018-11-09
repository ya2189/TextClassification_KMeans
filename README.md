# TextClassification_KMeans


                                                         Abstract

There are two major Machine Learning categories: Supervised Learning and Unsupervised Learning. Generally, Supervised Learning is when a machine is trained to predict the label or target of every input from the data, while having knowledge of the labels beforehand. Unsupervised Learning utilizes data that is not labeled or classified and tries to group the inputs, either through clustering or association. This paper focuses on text clustering with the Unsupervised Learning algorithm, the K-Means algorithm. The pre-processing procedure consists of removing stop words and other unimportant words, using TFID Vectorizer to create a sparse matrix of words weighted on their importance in the data set, and applying a matrix reduction method, Latent Semantic Analysis (LSA) to focus on the top 100 components of the data set. After these stages of pre-processing, the “Elbow Curve” is plotted to find the number of clusters, or “k”. Finally, with the optimized matrix of features and the number of clusters, the k-means algorithm is applied and plotted in a scatter plot (see Appendix B). 



                                                          Methods

Data Set Used, Basic Pre-Processing

The data set used in this paper is the Reuters-21578 test collection that is widely used for text categorization and analysis purposes. The data set is a collection of news articles with several attributes such as the title, date, places, and topics. For the purposes of text clustering via Unsupervised Learning, the topics or categories of the articles were not taken into consideration. Only the title and text body were extracted for analysis. The article text bodies underwent the first stage of pre-processing by removing all punctuation, numbers, and stop words. Stop words are words that have little relevance to the article itself and appear in every article in some way or form, such as “the, a, an, where, when or thus”. This step is done in the Java programming language. A text file is created of just the article bodies in an array format and is later used for the rest of the text analysis and clustering which is in the programming language Python. Before removing the stop words, the dimensions of the matrix, where the rows are articles and the columns are words, would have been 1855 x 134421, which is 1855 articles and 134421 words or features.



TFIDF Vectorizer

Afterwards, TFIDF Vectorizer is used to create a sparse matrix of weighted words. The TFIDF Vectorizer weights the words or features by importance regarding the data set or corpus. This weight is determined by calculating the frequency of the term, TF, and multiplying it by the “Inverse Document Frequency”, or IDF. The “Inverse Document Frequency” is calculated by taking the log of the number of articles divided by the number of articles the word is located in. With the TFID Vectorizer method in scikit learn, words that appear in 50% or more of the articles or appear less than 5 times in the articles are ignored. This step was taken to discard words that are not relevant to the articles or words that will skew the results because they appear in almost every article. In the same method, stop words are removed once again with a different list to ensure the accuracy of the clustering. 



Cosine Similarity Matrix

 Instead of calculating the correlation matrix, the cosine similarity is calculated between each pair of documents and is stored in a matrix (see Appendix C). Cosine similarity looks at the angle rather than the magnitude to see how related the pair of documents are. Cosine similarity was chosen over correlation because Cosine similarity only takes into consideration non-zero dimensions, which is ideal for the sparse matrix that was obtained from the TFID vectorizer.  



Latent Sematic Analysis- Dimension Reduction

The TFIDF Vectorizer leaves us with a normalized matrix with extremely large dimensions, specifically 1856 articles and 3129 features, that still contain many words that are not important to the analysis. Therefore, Latent Sematic Analysis or Truncated SVD (singular value decomposition) is used to perform linear dimensionality reduction. LSA is when a matrix with m articles and n words (m x n) is decomposed into three matrices, U, S, and VT. A value “k”, which represents the number of components or “concepts”, also needs to be chosen. These mathematical “concepts” are what the terms are associated with and are grouped by. The matrix that is most useful for this analysis is V, which is a m x k matrix with terms as rows and concepts as columns. This matrix contains weights of the terms according to their importance to their concept. In this analysis, k was chosen to be 100 components, which is recommended for LSA. Before transforming the matrix, the results are normalized again because LSA does not normalize the results. This allows the k-means algorithm to act as spherical k-means, which provides better results, rather than classical k-means. 



K-Means Algorithm

The K-Means algorithm is when centroids are randomly placed on a scatter plot of the data points, and the distance between the data point and the centroid is calculated. Then, the data point is associated with the closest centroid. The average of the distances is taken and the centroids are moved so that it is at the center of the closest data points. Doing this repeatedly creates “clusters” around the centroids. The “k” in the K-means algorithm correlates with the number of clusters. This number is obtained by using the method of plotting an “Elbow Curve” and locating where there is the sharpest curve, or the “elbow” in the graph (see Appendix A). For this analysis, the number of clusters is identified to be 3. With this information and the matrix that was attained, the k-means clustering is plotted in a scatter plot (See Appendix B). 



                                                          Results

The number of words extracted from the data set was originally 134,421 words. With pre-processing, which includes removing stop words, rarely occurring words, and too frequent words, the number was reduced to 3,129 words. Through LSA, or Truncated SVD, the matrix was further reduced to 100 components or mathematical “concepts”. Analyzing rows of the matrix V, from the decomposition of the sparse matrix, it is evident that most of the terms associated with a concept are similar to each other. For example, in component 29, some of the strongest terms in the component are “debt”, “tax”, “stock”, which are all similar terms that have to do with money or finances. In component 14, the strongest terms include “Reagan”, “president”, “chief”, “securities”, “executive”, and “officer, which all relate to the government, or government positions (see Appendix D). The Cosine similarity matrix tells us the similarity between each pair of articles. After multiple stages of pre-processing the data, the k-means clustering was performed with k = 3 clusters. The final scatter plot shows that the clusters are clean and have little to no interfere with each other (see Appendix B1). 



                                                    Discussion/Conclusions

The pre-processing stage of the analysis was long journey of researching efficient pre-processing methods, while switching back and forth from Java to Python, in search of the most realistic and efficient methods of removing useless words and reducing the immense matrix size. The basic pre-processing of removing stop words, punctuation, and numbers was done in Java. However, it was soon realized that it would be very difficult to reduce the matrix dimensions without tools that help with these procedures. Thus, sci-kit learn, a machine learning library with data analysis tools, was used to perform TFIDF vectorization, LSA, and K-means clustering. A tool called Count Vectorizer, in which a matrix of created of 1s and 0s according to if the term is present or not in the article, was considered before deciding on the TFIDF Vectorizer. However, this idea was discarded because TFIDF provided more information, such as the frequency of the terms, that would provide better clustering. Prior to attaining the final k-means clustering results, various scenarios were tested to find what attributes contributed to better clustering. The number of the features before LSA were first set to 300 features, but this displayed bad clustering (see Appendix B2). 500 features and then 1000 features were tested (see Appendix B3, B4). However, it became apparent that the increasing number of features displayed better and better clustering. Thus, the restriction of having a certain number of features was discarded, and all features were used (Note: This is the number of features before LSA, matrix reduction). The TFIDF vectorizer and LSA played key roles in pre-processing the data set to ensure more accurate results in clustering. Overall, the combination of these Unsupervised learning methods and k-means algorithm provided good clustering results. 
If the first set of articles were labeled with their classes, this would help to determine the labels of the second set of articles, because this is a direct application of how Supervised learning works for text classification. The data set is separated into a training set and a testing set, and the algorithm repeatedly predicts on the training set and learns to label the articles using the training set that is already labeled. Then, the testing set is able to become correctly labeled after this learning process. 



