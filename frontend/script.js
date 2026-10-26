// ---- LOGIN API ----
document.getElementById("loginForm")?.addEventListener("submit", async (e) => {
  e.preventDefault();

  const loginData = {
    voterId: document.getElementById("loginVoterId").value,
    biometricData: document.getElementById("loginBiometric").value
  };

  let res = await fetch(`${BASE_URL}/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(loginData)
  });

  let data = await res.json();
  document.getElementById("loginMessage").innerText = res.ok ? 
    "✅ Login Successful! Redirecting..." : 
    "❌ " + data.message;

  if (res.ok) setTimeout(() => window.location.href = "vote.html", 1000);
});

// ---- VOTE API ----
document.getElementById("voteForm")?.addEventListener("submit", async (e) => {
  e.preventDefault();

  const vote = {
    voterId: document.getElementById("voteVoterId").value,
    candidate: document.getElementById("candidate").value
  };

  let res = await fetch(`${BASE_URL}/vote`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(vote)
  });

  let data = await res.json();
  document.getElementById("voteMessage").innerText = res.ok ? 
    "✅ Vote Submitted Successfully!" : 
    "❌ " + data.message;
});

// ---- RESULTS API ----
async function loadResults() {
  let res = await fetch(`${BASE_URL}/results`);
  let data = await res.json();

  const table = document.getElementById("resultsTable");
  table.innerHTML = `<tr><th>Candidate</th><th>Votes</th></tr>`;

  data.forEach(row => {
    table.innerHTML += `<tr><td>${row.candidate}</td><td>${row.votes}</td></tr>`;
  });
}
