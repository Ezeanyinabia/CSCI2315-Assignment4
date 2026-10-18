package weiss.nonstandard;

public interface Stack<AnyType>
{
    boolean isEmpty( );
    void makeEmpty( );
    void push( AnyType x );
    void pop( );
    AnyType top( );
    AnyType topAndPop( );
}
