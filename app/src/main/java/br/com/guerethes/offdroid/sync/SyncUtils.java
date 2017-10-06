package br.com.guerethes.offdroid.sync;

import android.content.Context;
import android.util.Log;

import java.util.List;

import br.com.guerethes.offdroid.persist.model.Sincronizacao;
import br.com.guerethes.offdroid.query.business.QueryOffDroidManager;
import br.com.guerethes.offdroid.sync.operations.Operation;

public class SyncUtils {

    public static void sync(Context context) throws Exception {
        Log.i("Sync", "Verificando se há algo para ser sincronizado");

        QueryOffDroidManager queryOffDroidManager = QueryOffDroidManager.from(Sincronizacao.class, true, context);
        List<Sincronizacao> sincronizarList = (List<Sincronizacao>) queryOffDroidManager.toList();

        if ( sincronizarList != null && !sincronizarList.isEmpty() ) {
            for (Sincronizacao sincronizacao : sincronizarList) {

                Log.i("Sync",
                        "Sincronizando o dado " + sincronizacao.getValue().getClass()
                        + " com ID: " + sincronizacao.getValue().getId() );

                if (sincronizacao.getOperacao() == Operation.CREATE.ordinal()) {
                    QueryOffDroidManager.insert(sincronizacao.getValue(), context);
                } else if (sincronizacao.getOperacao() == Operation.UPDATE.ordinal()) {
                    QueryOffDroidManager.update(sincronizacao.getValue(), context);
                } else if (sincronizacao.getOperacao() == Operation.DELETE.ordinal()) {
                    QueryOffDroidManager.remove(sincronizacao.getValue(), context);
                }
            }
        } else {
            Log.i("Sync", "Não há informação para ser sincronizada.");
        }

    }

}