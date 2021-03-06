package ThePokerPlayer.actions;

import ThePokerPlayer.cards.PokerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ItsShowtimeAction extends AbstractGameAction {
	public ItsShowtimeAction() {
		this.duration = Settings.ACTION_DUR_FAST;
		this.actionType = ActionType.SPECIAL;
	}

	@Override
	public void update() {
		this.isDone = true;
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (!(c instanceof PokerCard)) {
				ShowdownAction.otherCards.add(c);
			}
		}
		if (AbstractDungeon.player.hand.group.size() <= ShowdownAction.otherCards.size()) {
			this.isDone = true;
			return;
		}
		AbstractDungeon.player.hand.group.removeAll(ShowdownAction.otherCards);

		AbstractDungeon.actionManager.addToBottom(new ShowdownAction(false));
	}
}
