package uom.team2.weball_statistics.Model.Actions.MatchFlow;

import uom.team2.weball_statistics.Model.Actions.Action;

public class MatchFlow extends Action {

    private FlowType flowType; //Start or pause

    public MatchFlow(String timeHappened, int id, FlowType flowType) {
        super(timeHappened, id);
        this.flowType = flowType;
        this.actionDesc = this.formatActionDesc();
    }

    @Override
    protected String formatActionDesc() {
        switch (this.flowType) {
            case PAUSE:
                return "Match Paused!";
            case RESUME:
                return "Match Continues!";
            case START:
                return  "Match Started!";
            case COMPLETED:
                return  "Match Completed";
            default:
                return "Undefined";
        }
    }
}