# Coding Skills Challenge

### The below describes a problem statement, make sure to read all the instructions in this readme before you start.

### Business Requirement:

- Company A have acquired Company B, A and B sell some common products, sourced from suppliers (Sometimes the same supplier, sometimes a different one). 
- The business wants to consolidate the product catalog into one superset (merged catalog).

### There are possibilities like:

- Company A and B could have conflicting product codes (SKUs).
- Product codes might be same, but they are different products.
- Product codes are different, but they are same product.
- You should not be duplicating product records in merged catalog.
- Product on merged catalog must have infromation about the comapny it belongs to originally.  

The business provided the following information that may help in identifying a matching product:
- Products have associated suppliers, each supplier provides 1 or many barcodes for a product, 
- A product may have many suppliers,
- If any supplier barcode matches for one product of company A with Company B then we can consider that those products as the same.


So, you have following entities to play with:

<img src="./entity_diagram.png" width="800px" height="auto">



You need to produce code in your preferred language which can demonstrate following:

### Initial load
- Mega merge: All products from both companies should get merge into a common catalog

### BAU mode
- A new product added in Catalog A
- An existing product removed from Catalog A
- An existing product in Catalog B got new supplier with set of barcodes
 

### Data Example: Feel free to create your own

Products for A
| SKU |	Description |
| --- | ----------- |
| 1   |	HammerA     |		
| 2   |	ShovelA     |		
| ..  |	..          |	

Products for B
| SKU |	Description |
| --- | ----------- |
| 1   |	HammerB     |		
| 2   |	ShovelB     |		
| ..  |	..          |		

Supplier
| ID |	Name        |
| --- | ----------- |
| 1   |	Etti        |		
| 2   |	Jerry       |		
| ..  |	..          |

SupplierProductBarCodes
| ID |	SKU         | Barcode	|
| --- | ----------- | -------	|
| 1   |	1           |	1234567	|	
| 2   |	1           |	5678901	|	
| ..  |	..          |		|	


### Deliverables.
- Application should be able to accept above data from text files as input and must produce a merged catalog in a text file as an output.
- Proving your code works via unit testing is highly encouraged.
- Spend as little or as much time as you like ⌚
- The code you produce can be in any language ⭐
- The output of the efforts ❗ must be committed back into a Public Repo in Github and the URL shared back for review. 

