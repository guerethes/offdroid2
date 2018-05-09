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

        if (sincronizarList != null && !sincronizarList.isEmpty()) {
            for (Sincronizacao sincronizacao : sincronizarList) {

                Log.i("Sync",
                        "Sincronizando o dado " + sincronizacao.getValue().getClass()
                                + " com ID: " + sincronizacao.getValue().getId());

                QueryOffDroidManager queryOperacao = QueryOffDroidManager.from(context);
                if (sincronizacao.getOperacao() == Operation.CREATE.ordinal()) {
                    queryOperacao.insert(sincronizacao.getValue());
                } else if (sincronizacao.getOperacao() == Operation.UPDATE.ordinal()) {
                    queryOperacao.update(sincronizacao.getValue());
                } else if (sincronizacao.getOperacao() == Operation.DELETE.ordinal()) {
                    queryOperacao.remove(sincronizacao.getValue());
                }
            }
        } else {
            Log.i("Sync", "Não há informação para ser sincronizada.");
        }

    }

}