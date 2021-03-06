package codevenger.timera;

import org.scribe.model.Verifier;

import codevenger.timera.authorization.OAuthFlickrService;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FlickrWebActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		openAuthorizationPage(OAuthFlickrService.authorizationUrl);	
	}
	public void openAuthorizationPage(String authorizationUrl) {
		final WebView webView = (WebView) findViewById(R.id.web_view);
		String ua = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
		webView.getSettings().setUserAgentString(ua);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.d("timera", url);
				if (url.startsWith("oauth")) {
					// authorization complete hide webview for now.
					webView.setVisibility(View.GONE);
					Uri uri = Uri.parse(url);
					String verifier = uri.getQueryParameter("oauth_verifier");
					Log.d("token", uri.getQuery());
					Intent i=new Intent();
					Bundle b=new Bundle();
					OAuthFlickrService.verifier = new Verifier(verifier);
					setResult(RESULT_OK);
					finish();
				}
				return false;
			}
		});
		webView.loadUrl(authorizationUrl);
	}
}
