package Stepping;

import Stepping.container.Container;
import Stepping.container.ContainerSingleton;

public abstract class AlgoBase extends IAlgo {

    private Q q = new Q();
    private Container cntr = new Container();

    protected AlgoBase(String id, int delay, int initialdelay){ super(id, delay, initialdelay);

    }

    @Override
    public void run() {
        while (true) {
            start(new Data<>());
        }
    }

    public AlgoInfraConfig init() {
        DI(new Subject(), "newDataArrivedSubject");
        DI(new SubjectContainer(), "subjectContainer");

        IoC();
        initSteps();
        attachSubjects();

        wakenProcessingUnit();
        return null;
    }

    private void initSteps(){
        Thread.currentThread().getThreadGroup().list();
        for (IStep step : cntr.<IStep>getTypeOf(IStep.class)) {
            step.init();
            step.setContainer(cntr);
        }
    }

    private void attachSubjects(){
        SubjectContainer subjectContainer = getContainer().getById("subjectContainer");
        for (IStep step : ContainerSingleton.getInstance().<IStep>getTypeOf(IStep.class)) {
            step.init();
            for (ISubject subject : subjectContainer.getSubjectsList()) {
                step.attach(subject);
            }
        }
    }

    protected SubjectContainer getSubjectContainer(){
        return cntr.getById("subjectContainer");
    }

    protected Container getContainer(){
        return cntr;
    }

    protected  <T> void DI(T obj, String id){
        cntr.add(obj, id);
    }

    public void newDataArrived(Data<?> data) {
       data.getValue();
       q.queue(data);

    }
    abstract protected void IoC();
    abstract protected void start(Data data);

}
