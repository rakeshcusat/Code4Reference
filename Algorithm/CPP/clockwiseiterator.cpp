#include <iostream>
#include <memory>
#include <cstdlib>
#include <vector>

using namespace std;

#define		R_SUCCESS			0
#define		TOP_ROW				1
#define		RIGHT_COLUMN		2
#define		BOTTOM_ROW			3
#define		LEFT_COLUMN			4


class ClockwiseIterator{
	private:
			int 					rows;
			int						columns;
			vector<vector<char> > 	two_d_array;   						/*vectors inside vector will act as an 2D array*/
			
	public:
			ClockwiseIterator()
			{
				rows = 0;
				columns = 0;
			}
			
			/**
			 * Read input through the console and initializes the object variable
			 */
			
			void read_input(){
				
				char value = 0;
				vector<char> tempvector;
				
				cout<<"Please enter #rows & #columns : ";
				cin>>rows>>columns; 									/*Read row and Column */
				
				cout<<"Please enter the values(only char) in  row-wise order"<<endl;
				
																		/*Reading the array elements value*/
				for(int outer_index = 0;outer_index<rows; outer_index++){
					
					tempvector.clear(); 								/*cleaning the vector before inserting new value for the row*/
					
					for(int inner_index = 0; inner_index <columns; inner_index++){
						cin>>value;
						tempvector.push_back(value);					/*Putting the values in the row*/
					}
					
					two_d_array.push_back(tempvector);  				/*putting the particular row in the vector*/
				}
			}
			
			/**
			 * This method display the 2d Array,(Here 2d array taken as vectors inside another vector)
			 */
			void display_2d_array(){
				
				vector<char> tempvector;
				
				cout<<"========2D Array======="<<endl;
				
				for(int outer_index = 0;outer_index<rows; outer_index++){
					
					tempvector = two_d_array.at(outer_index);
					
					for(int inner_index = 0; inner_index <columns; inner_index++){
						
						cout<<tempvector[inner_index]<<" ";
						
					}
					cout<<endl;
				}
			}
			
		/**
		 * This method prints the 2-d array in clockwise inward spiral
		 * 
		 */
		void print_inwardSpiral(){
				
				int direction		= TOP_ROW;
				/*x,y will represent the current co-ordinate*/	
				int x 				= 0;								
				int y 				= 0;
				/*high_x,high_y,low_x,low_y will decide about the ranges up to which value will be printed in particular rows or columns */
				int high_x 				= rows;								
				int high_y				= columns;
				int low_x 				= 0;
				int low_y 				= 0;	
				/*Total number of elements present in the 2D array*/
				int element_count	= rows * columns;
				
				cout<<"========clockwise inward spiral output======="<<endl;
				
				while(0 != element_count){
					
					cout<<two_d_array[x][y]<<" ";
					
					switch(direction){ 
						
						case TOP_ROW : 
								if(y+1 < high_y){						/*check if y remains in the boundary for top row, otherwise change direction*/
								   y++;
								}else{
									direction = RIGHT_COLUMN;
									low_x++;
									x++;
							    }
							   break;
							   
						case RIGHT_COLUMN:
								if(x+1 < high_x){						/*check if x remains in the boundary for right column, otherwise change direction*/
									x++;
								}else{
									direction = BOTTOM_ROW;
									high_y--;
									y--;
								}
								 break;
								 
						case BOTTOM_ROW :    
								if(y-1 >= low_y){						/*check if y remains in the boundary for bottom row,otherwise change direction*/
									y--;
								}
								else{
									direction = LEFT_COLUMN;
									high_x--;
									x--;
								}
								break;
								
						case LEFT_COLUMN:								/*check if x remains in the boundary for left column, otherwise change direction*/
								if(x-1 >= low_x){
									x--;
								}
								else{
									direction = TOP_ROW;
									low_y++;
									y++;
								}
								break;
								
						default:
								cout<<"[ERROR]:This state shouldn't be reached"<<endl;
								
						}
					element_count--;  									/*decrement the count*/
				}
				cout<<endl;
			}				
};

/**
 * Main method to create object and call the memember methods
 */
int main(int argc,  char **argv){
	
	ClockwiseIterator  obj;
	
	obj.read_input();
	obj.display_2d_array();
	obj.print_inwardSpiral();
	
	return R_SUCCESS;
}
