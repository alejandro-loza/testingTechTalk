import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class Fibonacci extends Specification {

    static final FIBONACCI = '1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144,…'

    def fib(n) {
        if (n < 2) return 1
        else return fib(n - 1) + fib(n - 2)
    }

    def 'Should give the fibonacci sequence at index 1'() {

        when: 'the index is 1'
        def result = fib(1)

        then:
        assert result == 1

    }

    def 'Should give the fibonacci sequence at index 4'() {

        when: 'the index is 4'
        def result = fib(4)

        then:
        assert result == 5

    }

    @Unroll
    @Ignore
    def 'Should th fibonacci number at index #index to equals #fibo'() {

        expect: 'the index is #index'
        fib(index) == fibo

        where:
        index | fibo
        0     | 1
        1     | 1
        2     | 2
        3     | 3
        4     | 5
        5     | 8
        6     | 13
        7     | 21
        8     | 34
        9     | 55
        10    | 89
        11    | 144

    }

}
