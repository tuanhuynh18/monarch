Rails.application.routes.draw do
  namespace :admin do
    resources :users
    resources :accommodations
    resources :activities
    resources :trips
    resources :addresses
    resources :places

    root to: "users#index"
  end

  resources :accommodations, only: %i[ index show ]
  resources :activities, only: %i[ index show ]
  resources :places, only: %i[ index show ]

  resources :trips do
    resources :accommodations, controller: 'trips/accommodations'
    resources :activities, controller: 'trips/activities'
    resources :places, controller: 'trips/places'
  end

  # For details on the DSL available within this file, see https://guides.rubyonrails.org/routing.html
  root "public#index"

  devise_for :users
end
