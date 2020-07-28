package immutable;

/**
 *   final的方法
 */
public class FinalMethodDemo {

    public void drink() {

    }

    public final void eat() {

    }

}

class SubClass extends FinalMethodDemo {
    @Override
    public void drink() {
        super.drink();
    }
}
