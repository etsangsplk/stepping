package algos.etlalgo;

import Stepping.*;

import java.util.List;

public class    InOutCounterDefaultStep implements Step {
    private long counterProduce;
    private long counterConsume;

    InOutCounterDefaultStep() {
    }

    @Override
    public void init() {

    }

    @Override
    public boolean followsSubject(String subjectType) {
        if(subjectType.equals(DefaultSubjectType.STEPPING_PUBLISH_DATA.name()) || subjectType.equals(DefaultSubjectType.STEPPING_DATA_ARRIVED.name())) {
           return true;
        }
        return false;
    }

    @Override
    public void newDataArrivedCallBack(Data data, SubjectContainer subjectContainer) {
        if(data.getSubjectType().equals(DefaultSubjectType.STEPPING_PUBLISH_DATA.name())) {
            counterProduce += ((List)data.getValue()).size();
        }

        if(data.getSubjectType().equals(DefaultSubjectType.STEPPING_DATA_ARRIVED.name())) {
           counterConsume += ((List)data.getValue()).size();
        }
    }

    @Override
    public void tickCallBack() {
        System.out.println("InOutCounterDefaultStep **** COUNTER PRODUCE ******* : " + counterProduce + "**** COUNTER CONSUME ******* : " + counterConsume);
        //System.out.println("**** COUNTER CONSUME ******* : " + counterConsume);
      //  throw new RuntimeException("test");
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
}
