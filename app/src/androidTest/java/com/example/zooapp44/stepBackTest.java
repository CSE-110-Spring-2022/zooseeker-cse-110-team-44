package com.example.zooapp44;

import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class stepBackTest {
    ExhibitRoute route;
    @Before
    public void initializeGraph(){
        ZooGraph graph = ZooGraph.getSingleton(ApplicationProvider.getApplicationContext());
        List<String> exhibits = Arrays.asList("flamingo", "gorilla", "toucan", "spoonbill");
        String start_location = "entrance_exit_gate";
        route = graph.getOptimalPath(start_location, exhibits);
    }

    @Test
    public void testStepBack(){
        Intent getDirectionIntent = new Intent(ApplicationProvider.getApplicationContext(), GetDirectionActivity.class);
        getDirectionIntent.putExtra("Route", ExhibitRoute.serialize(route));
        ActivityScenario<GetDirectionActivity> scenario = ActivityScenario.launch(getDirectionIntent);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            for(int i = 0; i < route.getSize()-2; i++){
                String preExhibit, postExhibit;
                Button nextButton = activity.findViewById(R.id.next_btn);
                Button backButton = activity.findViewById(R.id.back_btn);
                for(int j = 0; j < i + 1; j++) {
                    nextButton.performClick();
                }
                TextView textView = activity.findViewById(R.id.current_animal);
                preExhibit = textView.getText().toString();
                backButton.performClick();
                textView = activity.findViewById(R.id.current_animal);
                postExhibit = textView.getText().toString();
                assertEquals(preExhibit, route.exhibits.get(i+1));
                assertEquals(postExhibit, route.exhibits.get(i));
            }
        });
    }
}
