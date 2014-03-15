package fr.poulpi.pegasus.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;

import fr.poulpi.pegasus.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by pokito on 15/03/14.
 */

public class ItinarySearchCard extends Card {

    private CumulListLayout list;
    private ListCumulAdapter mAdapter = null;

    public ItinarySearchCard(Context context) {
        super(context, R.layout.itinary_search_card_inner_content);
        init();
    }

    private void init() {
        //Add Header
        CardHeader header = new CardHeader(getContext());
        header.setTitle(getContext().getResources().getString(R.string.cumul));
        addCardHeader(header);

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        list = (CumulListLayout) view.findViewById(R.id.cumul_card_inner_list);

        ArrayList<FriendCount> tmp = buildArrayHelper();
        mAdapter = new ListCumulAdapter(getContext(), event, tmp);

        list.setAdapter(mAdapter);
    }

    //------------------------------------------------------------------------------------------


    public ArrayList<FriendCount> buildArrayHelper() {

        ArrayList<FriendCount> list = new ArrayList<FriendCount>();
        Iterator<Friend> friendsIter = event.getFriends().iterator();
        int i = 0;

        while (friendsIter.hasNext()){

            list.add(new FriendCount(((EventActivity)getContext()), event, friendsIter.next(), FriendCount.CUMUL_MODE));

            i++;
        }

        return list;
    }


}
