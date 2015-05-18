package example.com.observertutorial;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        someFunction();
    }


    public void someFunction()
    {
        Observer observer = new Observer();

        Listener listener = new Listener();

        listener.setObserver(observer);

        observer.setSomeValue(3);//this will NOT trigger the onChange() method from the listener
        observer.setSomeValue(3, false);//this will also NOT trigger the onChange() method from the listener
        observer.setSomeValue(3, true);//this WILL trigger the onChange() method from the listener
        observer.notify();//this WILL trigger the onChange() method from the listener, even though nothing has changed
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class Observer extends SimpleObservable<Observer>
{
    private int someValue;

    public void setSomeValue(int someValue, boolean... isNotificationRequired) {
        this.someValue = someValue;
        if (isNotificationRequired.length > 0 && isNotificationRequired[0]) notifyObservers();
    }

    public int getSomeValue()
    {
        return this.someValue;
    }
}

class Listener implements OnChangeListener<Observer>
{
    Observer observerObj;

    public void setObserver(Observer observerObj)
    {
        this.observerObj = observerObj;

        //we need to set the current object as listener
        this.observerObj.addListener(this);
    }

    @Override
    public void onChange() {
        //do something
        Log.i("info", "some value: " + observerObj.getSomeValue());
    }
}
