package ThePokerPlayer.relics;

import ThePokerPlayer.PokerPlayerMod;
import ThePokerPlayer.cards.ClubsClub;
import ThePokerPlayer.cards.PokerCard;
import ThePokerPlayer.powers.ClubPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static ThePokerPlayer.patches.CustomTags.POKER_PLAYER_CLUB;

public class ClubPass extends CustomRelic {

	private static final String RAW_ID = "ClubPass";
	public static final String ID = PokerPlayerMod.makeID(RAW_ID);
	public static final String IMG = PokerPlayerMod.GetRelicPath(RAW_ID);
	public static final String OUTLINE = PokerPlayerMod.GetRelicOutlinePath(RAW_ID);

	public static final int AMOUNT = 2;

	public ClubPass() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.FLAT);
	}

	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (card instanceof PokerCard && ((PokerCard) card).suit == PokerCard.Suit.Club || card.hasTag(POKER_PLAYER_CLUB)) {
			this.flash();
			this.counter++;
			if (card instanceof ClubsClub) {
				this.counter++;
			}
			if (this.counter >= AMOUNT) {
				this.counter -= AMOUNT;
				this.flash();
				AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ClubPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));// 40
			}
		}
	}

	public void atBattleStart() {
		this.counter = 0;
	}

	public void onVictory() {
		this.counter = -1;
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ClubPass();
	}
}
