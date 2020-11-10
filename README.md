Test Coding Skills
The below describes a problem statement, make sure to read all of the instructions in this readme before you start.

Business Requirement:
Company A has acquired a Company B, A and B are selling some common set of products, sourced from same or different suppliers. 
Business wants to create a common catalog from both company product catalogs.

There are possibilities like:
Company A and B could have conflicting product codes.
Product codes might be same, but they are different products.
Product codes are different, but they are same product.
You should not be duplicating product records in merged catalog.
Merge catalog must be aware that a product sourced from Company A or B.

Business provided an information that may help in identifying a matching product:
These products have associated suppliers, each supplier provides some barcodes for a product, it means one product may have many suppliers and a supplier will have many bar codes for a product. Even If a single supplier's barcode matches for one product of company A with Company B then we can consider that those products are same.

So, you have following entities to play with:

Catalog A
Catalog B
Products of Catalog A
Products of Catalog B
Merged Catalog - C

You need to produce code in your preferred language which can demonstrate following:

Initial load
--Mega merge: All products from both companies should get merge into a common catalog

BAU mode
--A new product added in Catalog A
--An existing product removed from Catalog A
--An existing product in Catalog B got new supplier
 

Data Example: Feel free to create your own

Products for A
SKU	Description		
1	HammerA		
2	ShovelA		
..	..		

Products for B
SKU	Description		
1	HammerB		
2	ShovelB		
..	..		

Supplier
ID	Name			
1	Etti			
2	Jerry			
3	Vive			
	

SupplierProductBarCodes
ID 	SKU 	Barcode
1	1	1234567
2	1	5678901

Deliverables:
•	Application should be able to accept above data from text files as input and must produce a merged catalog in a text file as an output.
•	Spend as little or as much time as you like ⌚
•	The code you produce can be in any language ⭐
•	The output of the efforts ❗ must be committed back into a Public Repo in Github and the URL shared back for review. 
•	Proving your code works via unit testing is highly encouraged.
