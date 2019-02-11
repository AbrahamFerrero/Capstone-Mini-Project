# Capstone-Mini-Project by Abraham Ferrero
This is the final project for the course "Capstone: Analyzing (Social) Network Data", designed entirely by me from the scratch.

Communities within a Social Network

Overview

In this project we will work with real data, encrypted for privacy purposes. 
The first part will be about structuring that data in classes to be readable and to work with it as graphs and handle the info quickly,
and, given two ID´s, the algorithm will give us their friends in common, just as they do in facebook.
In the second part, having an ID, it will recommend us friendship with people with same features as that ID 
(political ideology, work, location, gender, etc).

Data

Real data provided here: https://snap.stanford.edu/data/egonets-Facebook.html


Easier question

For two given users, how many friends in common do they have? Who are they? 
For example, between Maria and Manuel, they have two friends in common and they are Josh and Kenny.


Algorithms, Data Structures, and Answer to your Question:

The network has been laid out as a classic graph using an adjacency list. 
Each individual in the graph is a vertex and an edge between vertices represents a friendship.

Algorithm

Input: Two specific user id’s Output: A list of friends in common.
Create a list of friends If any of the two friends is not in the graph, 
throws exception Get the id of the first user from the graph Get the id of the second user from the graph Get the list of neighbours
for both vertices Compare them, and it any id is in both lists as neighbours add that id to the list of friends return the list

Algorithm Analysis: Getting the vertex of both users from the graph using a HashMap is O(1). 
Getting their list of neighbours is also O(1) but then iterating over the HashSet to compare friends in common it
would be O(E2), being E the list of edges, as you iterate over the whole list twice. I think this is the most efficient approach.

Testing:
Really easy to test, I tested it with two graphs in the list, and gave me a correct print statement. 
I also tested it between a user that it didn’t exist in the graph and it thrown the exception as expected. 
Finally I tested it between two vertices with no friends in common and gave me a print statement telling me so. 
Reflection: No changes required. Maybe this easy question was a too easy question but everything came as expected so I could 
spend more time on the scanner for the data, improving my skills working with strings and parsers.

CODE OVERVIEW:

Class: Graph
Purpose and description of class: Represents the network graph.
Stores a list of vertices (each of which store their edges). 
Standard method exist for building the graph and querying facets of the graph, but also added the private method “friendsOfTwo” 
which is called in the public method friendsInCommon to print a statement giving the solution for the easy question.

Class: MapEdge
Represents an edge in a graph. Stores references to each of the vertices the edge connects.

Class: Vertex
Purpose and description of class: Represents a node in a graph. Keeps a list of edges to adjacent nodes in the graph. 
In the future it will have more features.

Overall Design Justification:
I decided to maintain a graph representation through adjacency list because it is in my opinion the most efficient way to work 
in the project with the knowledge I have. 
At the moment it is working perfectly and as expected.
