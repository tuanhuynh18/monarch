Rails.application.routes.draw do
  # resources :accommodations
  # resources :activities
  # resources :places

  resources :trips do
    resources :accommodations
    resources :activities
    resources :places
  end

  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  root "public#index"

  devise_for :users
end
