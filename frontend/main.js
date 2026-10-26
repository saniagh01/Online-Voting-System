const API = 'http://localhost:9090/api';

async function fetchCandidates(){
  const res = await fetch(API + '/candidates');
  const list = await res.json();
  const ul = document.getElementById('candidateList');
  const sel = document.getElementById('candidateSelect');
  ul.innerHTML = '';
  sel.innerHTML = '';
  list.forEach(c => {
    const li = document.createElement('li');
    li.textContent = `${c.id} — ${c.name} (${c.party || '-'}) — votes: ${c.votes}`;
    ul.appendChild(li);
    const opt = document.createElement('option');
    opt.value = c.id;
    opt.text = `${c.name} (${c.party || '-'})`;
    sel.appendChild(opt);
  });
}

document.getElementById('btnAddCandidate').onclick = async () => {
  const name = document.getElementById('candName').value;
  const party = document.getElementById('candParty').value;
  if(!name) return alert('candidate name required');
  const res = await fetch(API + '/candidates', {
    method:'POST', headers:{'Content-Type':'application/json'},
    body: JSON.stringify({name, party})
  });
  if(res.ok){
    document.getElementById('candName').value=''; document.getElementById('candParty').value='';
    fetchCandidates();
  } else {
    alert('Failed to add candidate');
  }
};

document.getElementById('btnRegister').onclick = async () => {
  const name = document.getElementById('regName').value;
  const email = document.getElementById('regEmail').value;
  if(!email) return alert('email required');
  const res = await fetch(API + '/voters/register', {
    method:'POST', headers:{'Content-Type':'application/json'},
    body: JSON.stringify({name, email})
  });
  const txt = document.getElementById('regResult');
  if(res.ok){
    const data = await res.json();
    txt.textContent = 'Registered. your voterId: ' + data.id;
  } else {
    const err = await res.text();
    txt.textContent = 'Error: ' + err;
  }
};

document.getElementById('btnVote').onclick = async () => {
  const voterId = Number(document.getElementById('voterId').value);
  const candidateId = Number(document.getElementById('candidateSelect').value);
  const res = await fetch(API + '/votes/cast', {
    method:'POST', headers:{'Content-Type':'application/json'},
    body: JSON.stringify({voterId, candidateId})
  });
  const out = document.getElementById('voteResult');
  if(res.ok){
    out.textContent = 'Voted successfully';
    fetchCandidates(); fetchResults();
  } else {
    const txt = await res.text();
    out.textContent = 'Error: ' + txt;
  }
};

document.getElementById('btnRefresh').onclick = fetchResults;

async function fetchResults(){
  const res = await fetch(API + '/votes/results');
  const list = await res.json();
  const ul = document.getElementById('resultsList');
  ul.innerHTML = '';
  list.forEach(c => {
    const li = document.createElement('li');
    li.textContent = `${c.name} — votes: ${c.votes}`;
    ul.appendChild(li);
  });
}

window.onload = () => {
  fetchCandidates();
  fetchResults();
};
