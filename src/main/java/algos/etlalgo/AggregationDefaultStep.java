package algos.etlalgo;

import Stepping.*;
import algos.etlalgo.dto.EtlTuple;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class AggregationDefaultStep implements Step {

    private Gson gson;

    public AggregationDefaultStep() {
        //super(AggregationDefaultStep.class.getName());
        gson = new Gson();
    }

//    @Override
//    public void attach(ISubject iSubject) {
//        if (iSubject.getType().equals(SubjectType.AGGREGATION.name())) {
//            iSubject.attach(this);
//        }
//    }

//    @Override
//    public void shutdown() {
//
//    }

    @Override
    public void tickCallBack() {
        System.out.println("AggregationDefaultStep TICKS");
    }

    @Override
    public void restate()  {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shuttingDown() {

    }

    @Override
    public void setContainer(Container cntr) {

    }

    @Override
    public void init() {

    }

    @Override
    public boolean followsSubject(String subjectType) {
        if (subjectType.equals(SubjectType.AGGREGATION.name())) {
            return true;
        }
        return false;
    }

    //todo add subjectContainer.getByName(DefaultSubjectType.STEPPING_PUBLISH_DATA.name()).setData(aggrTupples); to SubjectContainer?
    @Override
    public void newDataArrivedCallBack(Data data, SubjectContainer subjectContainer) {
        //* doing my stuff
        if (data.getSubjectType().equals(SubjectType.AGGREGATION.name())) {
            System.out.println("AggregationDefaultStep: preProcessedData Arrived!");
            System.out.println("AggregationDefaultStep: publishing data");
            List<EtlTuple> tupples = (List<EtlTuple>) data.getValue();
            List<JsonObject> aggrTupples = tupples.stream()
                    .distinct()
                    .map(etlTuple -> gson.toJsonTree(etlTuple).getAsJsonObject())
                    .collect(Collectors.toList());
            subjectContainer.getByName(DefaultSubjectType.STEPPING_PUBLISH_DATA.name()).setData(new Data(aggrTupples));
        }
    }
}
