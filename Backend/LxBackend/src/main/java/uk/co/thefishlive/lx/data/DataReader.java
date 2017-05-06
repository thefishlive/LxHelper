package uk.co.thefishlive.lx.data;

public interface DataReader<T, S>
{
    void handleData(T instance, S data);
}
