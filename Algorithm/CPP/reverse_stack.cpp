#include <iostream>
#include <stack>

using namespace std;

void appendStack(stack<int> *s, int a) 
{
	if (s->empty()) { 
	
		s->push(a);
		return;
	} else { 
		int o = s->top();
		s->pop(); 
		cout<<o<<" "<<s->size()<<endl;
		appendStack(s, a); 
		s->push(o); 
	}
}

void revertStack(stack<int> *s) 
{
	if (s->empty()) { 
		return; 
	} else { 
		int a = s->top();
		
		s->pop();
		
		revertStack(s);
		cout<<endl;
		appendStack(s, a);
	} 
}


void display_stack(stack<int> stackarg)
{
	cout<<"Stack :";
	while(!stackarg.empty())
	{
		cout<<stackarg.top()<< " ";
		stackarg.pop();
	}
	cout<<endl;
}
int main(int argc,  char **argv){

	stack<int> mystack;
	
	mystack.push(1);
	mystack.push(2);
	mystack.push(3);
	mystack.push(4);
	
	revertStack(&mystack);
	
	display_stack(mystack);
	
return 0;
}