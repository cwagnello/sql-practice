# Overview
Doing some SQL practice

# Projects

### SQL practice
The `sales` project is just a simple Proof-of-concept of creating tables and writing SQL to calculate tax for a given product. After generating the SQL I realized there are some improvements that can and should be made. One is to add a table for the `Product.type`. Two is to create another table for the `Location` so we can reference the location via an `id` from the client. The way I see a query being sent to the db is id's for the products being purchased would be passed in to the query with the location id. If we assume this is coming from a UI then having those id's is a way to make each item easily referenceable. 
