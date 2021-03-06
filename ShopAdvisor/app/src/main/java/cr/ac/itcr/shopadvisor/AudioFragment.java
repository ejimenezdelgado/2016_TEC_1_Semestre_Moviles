package cr.ac.itcr.shopadvisor;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.media.MediaPlayer.OnPreparedListener;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AudioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AudioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioFragment extends Fragment implements MediaPlayer.OnPreparedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //Variables para setear el componente de audio
    private MediaPlayer mp;
    private int posicion = 0;




    private OnFragmentInteractionListener mListener;

    public AudioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioFragment newInstance(String param1, String param2) {
        AudioFragment fragment = new AudioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_audio, container, false);

        Button btnAudio= (Button) view.findViewById(R.id.btnAudio);
        final Button btnAudioIniciar= (Button) view.findViewById(R.id.btnAudioIniciar);
        final Button btnAudioPausar= (Button) view.findViewById(R.id.btnAudioPausar);
        final Button btnAudioContinuar= (Button) view.findViewById(R.id.btnAudioContinuar);
        final Button btnAudioDetener= (Button) view.findViewById(R.id.btnAudioDetener);
        final Button btnAudioCircular= (Button) view.findViewById(R.id.btnAudioCircular);
        final Button btnAudioInternet= (Button) view.findViewById(R.id.btnAudioInternet);

        btnAudio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.ambiente);
                mp.start();
            }
        });

        btnAudioIniciar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.ambiente);
                mp.start();
                String op = btnAudioCircular.getText().toString();
                if (op.equals("no reproducir en forma circular"))
                    mp.setLooping(false);
                else
                    mp.setLooping(true);
            }
        });

        btnAudioPausar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mp != null && mp.isPlaying()) {
                    posicion = mp.getCurrentPosition();
                    mp.pause();
                }
            }
        });

        btnAudioContinuar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mp != null && mp.isPlaying() == false) {
                    mp.seekTo(posicion);
                    mp.start();
                }
            }
        });

        btnAudioDetener.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mp != null) {
                    mp.stop();
                    posicion = 0;
                }
            }
        });

        btnAudioCircular.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String op = btnAudioCircular.getText().toString();
                if (op.equals("no reproducir en forma circular"))
                    btnAudioCircular.setText("reproducir en forma circular");
                else
                    btnAudioCircular.setText("no reproducir en forma circular");
            }
        });


        btnAudioInternet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mp != null) {
                    mp.stop();
                    posicion = 0;
                }


                MediaPlayer mp = new MediaPlayer();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                try {
                    mp.setDataSource("http://www.javaya.com.ar/recursos/gato.mp3");
                    mp.prepareAsync();
                } catch (IOException e) {
                    Toast t = Toast.makeText(getActivity().getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_SHORT);
                }
                Toast t = Toast.makeText(getActivity().getApplicationContext(),
                        "Espere un momento mientras se carga el mp3",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        });
        return view;

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_audio, container, false);
    }



    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
