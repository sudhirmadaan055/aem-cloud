(function(){
  function initTwi(root){
    if(!root) return;
    var mediaWrap = root.querySelector('.c-twi__media');
    if(!mediaWrap) return;

    var type = root.getAttribute('data-type') || 'image';
    var src = root.getAttribute('data-src') || '';
    var poster = root.getAttribute('data-poster') || '';

    function showError(msg){
      var err = root.querySelector('.c-twi__error');
      if(err){ err.textContent = msg || 'Video unavailable'; err.style.display = 'block'; }
      mediaWrap.innerHTML = '<div class="c-twi__placeholder" aria-live="polite" style="display:flex;align-items:center;justify-content:center;color:#fff;background:#111;">'+(msg||'Video unavailable')+'</div>';
    }

    if(type === 'video'){
      var video = document.createElement('video');
      video.className = 'c-twi__video';
      video.playsInline = true;
      video.preload = 'metadata';
      if(poster) video.poster = poster;
      // do not show controls until play
      video.controls = false;

      var source = document.createElement('source');
      source.src = src;
      source.type = (src.endsWith('.mp4') ? 'video/mp4' : '');
      video.appendChild(source);

      video.addEventListener('error', function(){ showError('Video unavailable'); });

      mediaWrap.innerHTML = '';
      mediaWrap.appendChild(video);

      var play = document.createElement('button');
      play.className = 'c-twi__play';
      play.setAttribute('aria-label','Play video');
      play.innerHTML = '<svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg"><path d="M8 5v14l11-7z"/></svg>';
      mediaWrap.appendChild(play);

      play.addEventListener('click', function(){
        try{
          play.style.display = 'none';
          video.controls = true;
          var p = video.play();
          if(p && typeof p.catch === 'function'){
            p.catch(function(){ showError('Video unavailable'); });
          }
        }catch(e){ showError('Video unavailable'); }
      });
    } else {
      var img = document.createElement('img');
      img.className = 'c-twi__img';
      img.src = src;
      img.alt = root.getAttribute('data-alt') || '';
      img.addEventListener('error', function(){
        showError('Image unavailable');
      });
      mediaWrap.innerHTML = '';
      mediaWrap.appendChild(img);
    }
  }

  document.addEventListener('DOMContentLoaded', function(){
    document.querySelectorAll('.js--twi').forEach(initTwi);
  });
})();
