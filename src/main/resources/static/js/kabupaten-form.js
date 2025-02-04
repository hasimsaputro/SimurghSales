(() => {
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8082/api/kabupaten";
        let isLoading = false;

        function fetchSuggestions(query = '', target) {
            if (isLoading) return;
            isLoading = true;

            let apiUrl = `${target}-options`;

            fetch(`${mainUrl}/${apiUrl}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    isLoading = false;
                    let items = [];
                    let filteredData = [];

                    items = data.map(option => option.name);
                    console.log(items);
                    filteredData = query
                        ? items.filter(item => item.toLowerCase().includes(query.toLowerCase()))
                        : items;

                    const suggestionsContainer = document.querySelector(`.suggestions.${target}`);
                    suggestionsContainer.innerHTML = '';

                    if (filteredData.length > 0) {
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = item;

                            div.addEventListener('click', function () {
                                const inputField = document.querySelector(`input#${target}`);
                                inputField.value = item;

                                suggestionsContainer.innerHTML = '';
                            });

                            suggestionsContainer.appendChild(div);
                        });
                    } else {
                        const noResultMessage = document.createElement('div');
                        noResultMessage.classList.add('suggestion-item');
                        noResultMessage.textContent = 'Tidak ada hasil ditemukan.';
                        suggestionsContainer.appendChild(noResultMessage);
                    }
                })
                .catch(error => {
                    console.error('Terjadi kesalahan saat mengambil data:', error);
                    const suggestionsContainer = document.querySelector(`.suggestions.${target}`);
                    suggestionsContainer.innerHTML = '<div class="suggestion-item">Terjadi kesalahan saat memuat data.</div>';
                    isLoading = false;
                });
        }

        document.querySelectorAll('input').forEach(inputField => {
            inputField.addEventListener('input', () => {
                const query = inputField.value;
                const target = inputField.classList[0].replace('search-', '');
                fetchSuggestions(query, target);
            });
        });

        document.querySelectorAll('.search-icon').forEach(searchIcon => {
            searchIcon.addEventListener('click', () => {
                const target = searchIcon.getAttribute('data-target');
                const query = document.querySelector(`input#${target}`).value;
                fetchSuggestions(query, target);
            });
        });

        document.addEventListener('click', function(event) {
            const isClickInside = event.target.closest('.search-container');
            if (!isClickInside) {
                document.querySelectorAll('.suggestions').forEach(container => {
                    container.innerHTML = '';
                });
            }
        });
    });
})();
